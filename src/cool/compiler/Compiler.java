package cool.compiler;

import cool.ast.nodes.*;
import cool.ast.visitor.*;
import cool.structures.Scope;
import cool.structures.symbol.ClassSymbol;
import cool.structures.symbol.Symbol;
import cool.structures.symbol.TypeSymbol;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cool.lexer.*;
import cool.parser.*;
import cool.structures.SymbolTable;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
    // Annotates class ast with the names of files where they are defined.
    public static ParseTreeProperty<String> fileNames = new ParseTreeProperty<>();

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("No file(s) given");
            return;
        }
        
        CoolLexer lexer = null;
        CommonTokenStream tokenStream = null;
        CoolParser parser = null;
        ParserRuleContext globalTree = null;
        
        // True if any lexical or syntax errors occur.
        boolean lexicalSyntaxErrors = false;
        
        // Parse each input file and build one big parse tree out of
        // individual parse trees.
        for (var fileName : args) {
            var input = CharStreams.fromFileName(fileName);
            
            // Lexer
            if (lexer == null)
                lexer = new CoolLexer(input);
            else
                lexer.setInputStream(input);

            // Token stream
            if (tokenStream == null)
                tokenStream = new CommonTokenStream(lexer);
            else
                tokenStream.setTokenSource(lexer);
                
            /*
            // Test lexer only.
            tokenStream.fill();
            List<Token> tokens = tokenStream.getTokens();
            tokens.stream().forEach(token -> {
                var text = token.getText();
                var name = CoolLexer.VOCABULARY.getSymbolicName(token.getType());

                System.out.println(text + " : " + name);
                //System.out.println(token);
            });
            */
            
            // Parser
            if (parser == null)
                parser = new CoolParser(tokenStream);
            else
                parser.setTokenStream(tokenStream);
            
            // Customized error listener, for including file names in error
            // messages.
            var errorListener = new BaseErrorListener() {
                public boolean errors = false;
                
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer,
                                        Object offendingSymbol,
                                        int line, int charPositionInLine,
                                        String msg,
                                        RecognitionException e) {
                    String newMsg = "\"" + new File(fileName).getName() + "\", line " +
                                        line + ":" + (charPositionInLine + 1) + ", ";
                    
                    Token token = (Token)offendingSymbol;
                    if (token.getType() == CoolLexer.ERROR)
                        newMsg += "Lexical error: " + token.getText();
                    else
                        newMsg += "Syntax error: " + msg;
                    
                    System.err.println(newMsg);
                    errors = true;
                }
            };
            
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            
            // Actual parsing
            var tree = parser.program();
            if (globalTree == null)
                globalTree = tree;
            else
                // Add the current parse tree's children to the global tree.
                for (int i = 0; i < tree.getChildCount(); i++)
                    globalTree.addAnyChild(tree.getChild(i));
                    
            // Annotate class ast with file names, to be used later
            // in semantic error messages.
            for (int i = 0; i < tree.getChildCount(); i++) {
                var child = tree.getChild(i);
                // The only ParserRuleContext children of the program node
                // are class ast.
                if (child instanceof ParserRuleContext)
                    fileNames.put(child, fileName);
            }
            
            // Record any lexical or syntax errors.
            lexicalSyntaxErrors |= errorListener.errors;
        }

        // Stop before semantic analysis phase, in case errors occurred.
        if (lexicalSyntaxErrors) {
            System.err.println("Compilation halted");
            return;
        }

        // build AST
        var ast = new AstBuilder().visit(globalTree);
//      ast.print(0);

        // Populate global scope.
        SymbolTable.defineBasicClasses();

        // Semantic analysis
        TypeCheck1Visitor v1 = new TypeCheck1Visitor();
        ast.accept(v1);
        TypeCheck2Visitor v2 = new TypeCheck2Visitor();
        ast.accept(v2);
        TypeCheck3Visitor v3 = new TypeCheck3Visitor();
        ast.accept(v3);
        TypeCheck4Visitor v4 = new TypeCheck4Visitor();
        ast.accept(v4);

        if (SymbolTable.hasSemanticErrors()) {
            System.err.println("Compilation halted");
            return;
        }

        var group = new STGroupFile("cgen.stg");
        ST program = group.getInstanceOf("program");
        CodeGenVisitor vCodeGen = new CodeGenVisitor();
        vCodeGen.st = program;
        ast.accept(vCodeGen);

        List<TypeSymbol> classes = new ArrayList<>(SymbolTable.types.keySet());

        for (var x : classes)
            if (!x.getName().equals("SELF_TYPE"))
                program.add("objTab", x.getName());


        ST class_protObj = group.getInstanceOf("class_protObj");
        for (int i = 6; i < classes.size(); ++i) {
            ST protObj = group.getInstanceOf("protObj");
            protObj.add("className", classes.get(i).getName());
            protObj.add("tag", i-1);
            class_protObj.add("protObj", protObj);
        }
        program.add("class_protObj", class_protObj);


        ST class_dispTab = group.getInstanceOf("class_dispTab");
        for (var x : classes)
            if (!x.getName().equals("SELF_TYPE")) {
                ST c_dispTab = group.getInstanceOf("c_dispTab");
                c_dispTab.add("className", x.getName());
                List<String> methods = SymbolTable.types.get(x).getAllMethods();
                c_dispTab.add("method", methods);
                class_dispTab.add("c_dispTab", c_dispTab);
            }
        program.add("class_dispTab", class_dispTab);


        ST class_init = group.getInstanceOf("class_init");
        for (var x : classes)
            if (!x.getName().equals("SELF_TYPE") && !x.getName().equals("Object")) {
                ST c_init = group.getInstanceOf("c_init");
                c_init.add("className", x.getName());
                c_init.add("parent", SymbolTable.types.get(x).parent.getClassSym().getName());
                class_init.add("c_init", c_init);
            }
        program.add("class_init", class_init);


        List<String> strings = new ArrayList<>();
        List<Integer> integers = new ArrayList<>();

        strings.add("");

        for (var x : classes)
            if (!x.getName().equals("SELF_TYPE"))
                strings.add(x.getName());

        ST class_nameTab = group.getInstanceOf("class_nameTab");
        for (int i = 0; i < strings.size(); ++i)
            class_nameTab.add("id", i);
        program.add("class_nameTab", class_nameTab);

        ST str_const = group.getInstanceOf("str_const");
        for (int i = 0; i < strings.size(); ++i) {
            ST str_c = group.getInstanceOf("str_c");
            str_c.add("idStr", i);
            str_c.add("val", strings.get(i));

            int sz = strings.get(i).length();
            if (!integers.contains(sz))
                integers.add(sz);
            str_c.add("idIntSz", integers.indexOf(sz));
            int structSz =  (sz + 1) / 4 + 4;
            if ((sz + 1) % 4 != 0)
                structSz++;
            str_c.add("structSz", structSz);
            str_const.add("str_c", str_c);
        }
        program.add("str_const", str_const);


        ST int_const = group.getInstanceOf("int_const");
        for (int i = 0; i < integers.size(); ++i) {
            ST int_c = group.getInstanceOf("int_c");
            int_c.add("id", i);
            int_c.add("val", integers.get(i));
            int_const.add("int_c", int_c);
        }
        program.add("int_const", int_const);


        System.out.println(program.render());
    }
}
