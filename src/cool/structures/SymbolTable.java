package cool.structures;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import cool.ast.nodes.ClassDef;
import cool.ast.nodes.INode;
import cool.structures.symbol.*;
import org.antlr.v4.runtime.*;

import cool.compiler.Compiler;
import cool.parser.CoolParser;

public class SymbolTable {
    public static Map<Symbol, INode> symbolsNode = new HashMap<>();
    public static Map<TypeSymbol, ClassSymbol> types = new LinkedHashMap<>();
    public static Scope globals;
    public static ClassSymbol object;

    private static boolean semanticErrors;
    
    public static void defineBasicClasses() {
        globals = new DefaultScope(null);
        semanticErrors = false;
        
        // Populate global scope.
        object = new ClassSymbol("Object", globals);
        TypeSymbol type = new TypeSymbol("Object");
        object.typeSymbol = type;
        types.put(type, object);
        globals.add(type);

        MethodSymbol method = new MethodSymbol("abort", object);
        method.typeSymbol = object.typeSymbol;
        object.add(method);


        ClassSymbol symInt = new ClassSymbol("Int", object);
        type = new TypeSymbol("Int");
        symInt.typeSymbol = type;
        types.put(type, symInt);
        globals.add(type);

        ClassSymbol symString = new ClassSymbol("String", object);
        type = new TypeSymbol("String");
        symString.typeSymbol = type;
        types.put(type, symString);
        globals.add(type);

        method = new MethodSymbol("type_name", object);
        method.typeSymbol = symString.typeSymbol;
        object.add(method);

        method = new MethodSymbol("length", symString);
        method.typeSymbol = symInt.typeSymbol;
        symString.add(method);

        method = new MethodSymbol("concat", symString);
        method.typeSymbol = symString.typeSymbol;
        symString.add(method);

        var f = new IdSymbol("s");
        f.typeSymbol = symString.typeSymbol;
        method.add(f);

        method = new MethodSymbol("substr", symString);
        method.typeSymbol = symString.typeSymbol;
        symString.add(method);

        f = new IdSymbol("i");
        f.typeSymbol = symInt.typeSymbol;
        method.add(f);

        f = new IdSymbol("l");
        f.typeSymbol = symInt.typeSymbol;
        method.add(f);

        ClassSymbol sym = new ClassSymbol("Bool", object);
        type = new TypeSymbol("Bool");
        sym.typeSymbol = type;
        types.put(type, sym);
        globals.add(type);

        ClassSymbol selfTypeSym = new ClassSymbol("SELF_TYPE", null);
        type = new TypeSymbol("SELF_TYPE");
        selfTypeSym.typeSymbol = type;
        types.put(type, selfTypeSym);
        globals.add(type);

        method = new MethodSymbol("copy", object);
        method.typeSymbol = selfTypeSym.typeSymbol;
        object.add(method);

        sym = new ClassSymbol("IO", object);
        type = new TypeSymbol("IO");
        sym.typeSymbol = type;
        types.put(type, sym);
        globals.add(type);

        method = new MethodSymbol("out_string", sym);
        method.typeSymbol = selfTypeSym.typeSymbol;
        sym.add(method);

        f = new IdSymbol("x");
        f.typeSymbol = symString.typeSymbol;
        method.add(f);

        method = new MethodSymbol("out_int", sym);
        method.typeSymbol = selfTypeSym.typeSymbol;
        sym.add(method);

        f = new IdSymbol("x");
        f.typeSymbol = symInt.typeSymbol;
        method.add(f);

        method = new MethodSymbol("in_string", sym);
        method.typeSymbol = symString.typeSymbol;
        sym.add(method);

        method = new MethodSymbol("in_int", sym);
        method.typeSymbol = symInt.typeSymbol;
        sym.add(method);


    }
    
    /**
     * Displays a semantic error message.
     * 
     * @param ctx Used to determine the enclosing class context of this error,
     *            which knows the file name in which the class was defined.
     * @param info Used for line and column information.
     * @param str The error message.
     */
    public static void error(ParserRuleContext ctx, Token info, String str) {
        while (! (ctx.getParent() instanceof CoolParser.ProgramContext))
            ctx = ctx.getParent();
        
        String message = "\"" + new File(Compiler.fileNames.get(ctx)).getName()
                + "\", line " + info.getLine()
                + ":" + (info.getCharPositionInLine() + 1)
                + ", Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static void error(String str) {
        String message = "Semantic error: " + str;
        
        System.err.println(message);
        
        semanticErrors = true;
    }
    
    public static boolean hasSemanticErrors() {
        return semanticErrors;
    }
}
