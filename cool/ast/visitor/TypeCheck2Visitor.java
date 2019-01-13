package cool.ast.visitor;

import cool.ast.nodes.*;
import cool.parser.CoolParser;
import cool.structures.DefaultScope;
import cool.structures.Scope;
import cool.structures.SymbolTable;
import cool.structures.symbol.*;

public class TypeCheck2Visitor extends Visitor{
    private Scope currentScope = SymbolTable.globals;
    Symbol typeInt = SymbolTable.globals.lookup("Int");

    @Override
    public void visit(Program n) {
        for (var c : n.children)
            c.accept(this);
    }

    @Override
    public void visit(ClassDef n) {
        if (n.hasInheritance()) {
            if (n.id2.equals("SELF_TYPE")) {
                SymbolTable.error(n.ctx, n.ctx.baseClass, "Class " + n.id1 + " has illegal parent SELF_TYPE");
                return;
            }
            if (n.id2.equals("Int")) {
                SymbolTable.error(n.ctx, n.ctx.baseClass, "Class " + n.id1 + " has illegal parent Int");
                return;
            }
            if (currentScope.lookup(n.id2) == null) {
                SymbolTable.error(n.ctx, n.ctx.baseClass, "Class " + n.id1 + " has undefined parent " + n.id2);
                return;
            }

            TypeSymbol s = (TypeSymbol) SymbolTable.globals.lookup(n.id2);
            n.baseClass = (ClassDef) SymbolTable.symbolsNode.get(s);
            n.scope.setParent(SymbolTable.types.get(s));

            boolean cycle = false;
            for (ClassDef x = n.baseClass; !cycle && x != null; x = x.baseClass)
                if (x == n)
                    cycle = true;
            if (cycle) {
                SymbolTable.error(n.ctx, n.ctx.className, "Inheritance cycle for class " + n.id1);
                for (ClassDef x = n.baseClass; x != n; x = x.baseClass)
                    SymbolTable.error(x.ctx, x.ctx.className, "Inheritance cycle for class " + x.id1);
            }
        }

        Scope oldScope = currentScope;
        currentScope = n.scope;

        for (var c : n.children)
            c.accept(this);

        currentScope = oldScope;
    }

    @Override
    public void visit(VarDef n) {
        // La definirea unei variabile, creăm un nou simbol.
        var sym = new IdSymbol(n.id.id);

        Symbol typeSymbol = SymbolTable.globals.lookup(n.type);
        if (n.id.id.equals("self"))
            SymbolTable.error(n.ctx, n.ctx.idName, "Class " + ((ClassSymbol) currentScope).getName() + " has attribute with illegal name self");
        else if (!((ClassSymbol)currentScope).addVar(sym))
            SymbolTable.error(n.ctx, n.ctx.idName, "Class " + ((ClassSymbol) currentScope).getName() + " redefines attribute " + n.id.id);
        else if (typeSymbol == null) {
            SymbolTable.error(n.ctx, n.ctx.typeName, "Class " + ((ClassSymbol) currentScope).getName() + " has attribute " + n.id.id + " with undefined type " + n.type);
            return;
        }

        sym.typeSymbol = (TypeSymbol) typeSymbol;
        n.typeSymbol = sym.typeSymbol;


//        for (int i = 0; i < n.children.size(); ++i)
//            n.children.get(i).accept(this);
    }

    @Override
    public void visit(MethodDef n) {
        // La definirea unei variabile, creăm un nou simbol.
        var sym = new MethodSymbol(n.id, currentScope);

        Symbol s = SymbolTable.globals.lookup(n.type);
        sym.typeSymbol = (TypeSymbol) s;
        n.typeSymbol = sym.typeSymbol;

        if (!currentScope.add(sym)) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Class " + ((ClassSymbol) currentScope).getName() + " redefines method " + n.id);
            return;
        }
        SymbolTable.symbolsNode.put(sym, n);

        n.scope = sym;
        Scope oldScope = currentScope;
        currentScope = sym;

        for (var c : n.children)
            c.accept(this);

        currentScope = oldScope;
    }

    @Override
    public void visit(Formal n) {
        var sym = new IdSymbol(n.id);
        if (n.id.equals("self")) {
            SymbolTable.error(n.ctx, n.ctx.idName, "Method " + ((MethodSymbol) currentScope).getName() + " of class " + ((ClassSymbol) ((MethodSymbol) currentScope).parent).getName() + " has formal parameter with illegal name self");
            return;
        }
        if (!currentScope.add(sym)) {
            SymbolTable.error(n.ctx, n.ctx.idName, "Method " + ((MethodSymbol) currentScope).getName() + " of class " + ((ClassSymbol) ((MethodSymbol) currentScope).parent).getName() + " redefines formal parameter " + n.id);
            return;
        }
        if (n.type.equals("SELF_TYPE")) {
            SymbolTable.error(n.ctx, n.ctx.typeName, "Method " + ((MethodSymbol) currentScope).getName() + " of class " + ((ClassSymbol) ((MethodSymbol) currentScope).parent).getName() + " has formal parameter " + n.id + " with illegal type SELF_TYPE");
            return;
        }
        if (SymbolTable.globals.lookup(n.type) == null) {
            SymbolTable.error(n.ctx, n.ctx.typeName, "Method " + ((MethodSymbol) currentScope).getName() + " of class " + ((ClassSymbol) ((MethodSymbol) currentScope).parent).getName() + " has formal parameter " + n.id + " with undefined type " + n.type);
            return;
        }

        Symbol s = SymbolTable.globals.lookup(n.type);
        sym.typeSymbol = ((TypeSymbol) s);

        n.scope = currentScope;
        n.typeSymbol = sym.typeSymbol;
    }

    @Override
    public void visit(Case n) {
        for (int i = 0; i < n.ctx.case_branch().size(); ++i) {
            CoolParser.Case_branchContext c = n.ctx.case_branch(i);
            if (c.ID().getText().equals("self")) {
                SymbolTable.error(c, c.ID().getSymbol(), "Case variable has illegal name self");
                continue;
            }

            if (c.TYPE().getText().equals("SELF_TYPE")) {
                SymbolTable.error(c, c.TYPE().getSymbol(), "Case variable " + c.ID().getText() + " has illegal type SELF_TYPE");
                continue;
            }

            if (SymbolTable.globals.lookup(c.TYPE().getText()) == null)
                SymbolTable.error(c, c.TYPE().getSymbol(), "Case variable " + c.ID().getText() + " has undefined type " + c.TYPE().getText());
        }
    }
}
