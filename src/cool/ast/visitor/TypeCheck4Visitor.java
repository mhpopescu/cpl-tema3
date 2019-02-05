package cool.ast.visitor;

import cool.ast.nodes.*;
import cool.structures.DefaultScope;
import cool.structures.Scope;
import cool.structures.SymbolTable;
import cool.structures.symbol.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class TypeCheck4Visitor extends Visitor {
    private Scope currentScope = SymbolTable.globals;
    private Symbol typeInt = SymbolTable.globals.lookup("Int");
    private Symbol typeBool = SymbolTable.globals.lookup("Bool");
    private Symbol typeObject = SymbolTable.globals.lookup("Object");
    private Symbol typeSelf = SymbolTable.globals.lookup("SELF_TYPE");

    @Override
    public void visit(Program n) {
        for (var c : n.children)
            c.accept(this);
    }

    /*
     * Child inherits Parent -> c.parent = p
     * p = c  correct
     * c = p  INCORRECT
     * search if Child has parent Parent
    **/
    private boolean correctTypes(TypeSymbol p, TypeSymbol c) {
        if (p.getName().equals("SELF_TYPE")) {
            ClassSymbol objClass = currentScope.getClassSym();
            p = objClass.typeSymbol;
        }
        ClassSymbol classSym = SymbolTable.types.get(c);
        if (classSym == null)
            return true;
        return classSym.lookupType(p) != null;
    }

    @Override
    public void visit(ClassDef n) {
        Scope oldScope = currentScope;
        currentScope = n.scope;

        for (var c : n.children)
            c.accept(this);

        currentScope = oldScope;
    }

    @Override
    public void visit(VarDef n) {
        if (n.expr == null)
            return;

        n.expr.accept(this);
        // id = expr
        TypeSymbol t = n.expr.typeSymbol;
        IdSymbol s = (IdSymbol) currentScope.lookupVar(n.id.id);

        if (n.typeSymbol == null)
            return;
        if (!correctTypes(n.typeSymbol, t))
            SymbolTable.error(n.ctx, n.ctx.expr().start, "Type " + t.getName() + " of initialization expression of attribute " + n.id.id + " is incompatible with declared type " + n.typeSymbol);

        n.typeSymbol = s.typeSymbol;
    }

    @Override
    public void visit(MethodDef n) {
        Scope oldScope = currentScope;
        currentScope = n.scope;

        for (var c : n.children)
            c.accept(this);

        ClassSymbol objClass = currentScope.getClassSym();

        TypeSymbol t = n.children.get(n.children.size()-1).typeSymbol;
        if (t != typeSelf && !correctTypes(n.typeSymbol, t)) // do not use this because here SELF_TYPE should not be accepted
                SymbolTable.error(n.ctx, n.ctx.expr().start, "Type " + t.getName() + " of the body of method " + ((MethodSymbol)n.scope).getName() + " is incompatible with declared return type " + n.typeSymbol);

        if (n.children.get(n.children.size()-1).typeSymbol == typeSelf)
            n.typeSymbol = objClass.typeSymbol;


        currentScope = oldScope;
    }

    @Override
    public void visit(MethodCall n) {
        for (var c : n.children)
            c.accept(this);

//        TypeSymbol objectType = n.children.get(0).typeSymbol;
//        ClassSymbol objectClass = SymbolTable.types.get(objectType);
        ClassSymbol objectClass = currentScope.getClassSym();
//        self fixme
        if (objectClass == null)
            return;
        MethodSymbol methodSym = (MethodSymbol) objectClass.lookup(n.id);
        TypeSymbol objectType = objectClass.typeSymbol;

        if (methodSym == null) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Undefined method " + n.id + " in class " + objectType.getName());
            return;
        }
        n.typeSymbol = methodSym.typeSymbol;

        if (n.children.size() != methodSym.symbols.size()) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Method " + n.id + " of class " + objectType.getName() + " is applied to wrong number of arguments");
            return;
        }
        for (int i = 0; i < n.tokens.size(); ++i) {
            INode actual = n.children.get(i);
            IdSymbol formal = (IdSymbol)  methodSym.symbols.values().toArray()[i];
//            if (actual.typeSymbol != formal.typeSymbol)
            if (!correctTypes(formal.typeSymbol, actual.typeSymbol)) {
                SymbolTable.error(n.ctx, n.tokens.get(i), "In call to method " + n.id + " of class " + objectType.getName() + ", actual type " + actual.typeSymbol.getName() + " of formal parameter " + formal.getName() + " is incompatible with declared type " + formal.typeSymbol.getName());
                return;
            }
        }
    }

    @Override
    public void visit(ObjMethodCall n) {
        for (var c : n.children)
            c.accept(this);

//        n.typeSymbol = n.children.get(0).typeSymbol;
        TypeSymbol objectType = n.children.get(0).typeSymbol;
        TypeSymbol obj = objectType;
        ClassSymbol objectClass = SymbolTable.types.get(objectType);
//        self fixme
        if (objectClass == null)
            return;
        MethodSymbol methodSym = (MethodSymbol) objectClass.lookup(n.id);

        if (n.type != null && n.type.equals("SELF_TYPE")) {
            SymbolTable.error(n.ctx, n.ctx.TYPE().getSymbol(), "Type of static dispatch cannot be SELF_TYPE");
            return;
        }

        if (n.type != null) {
            TypeSymbol tStaticDispatch = (TypeSymbol) SymbolTable.globals.lookup(n.type);
            if (tStaticDispatch == null) {
                SymbolTable.error(n.ctx, n.ctx.TYPE().getSymbol(), "Type " + n.type + " of static dispatch is undefined");
                return;
            }
            if (objectClass.lookupType(tStaticDispatch) == null) {
                SymbolTable.error(n.ctx, n.ctx.TYPE().getSymbol(), "Type " + n.type + " of static dispatch is not a superclass of type " + objectClass.typeSymbol.getName());
                return;
            }
            objectClass = SymbolTable.types.get(tStaticDispatch);
            objectType = objectClass.typeSymbol;
            methodSym = (MethodSymbol) objectClass.lookup(n.id);
        }

        if (objectType == typeSelf) {
            objectClass = currentScope.getClassSym();
            objectType = objectClass.typeSymbol;
            methodSym = (MethodSymbol) objectClass.lookup(n.id);
        }

        if (methodSym == null) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Undefined method " + n.id + " in class " + objectType.getName());
            return;
        }

        n.typeSymbol = methodSym.typeSymbol;
        if (methodSym.typeSymbol.getName().equals("SELF_TYPE") && !obj.getName().equals("SELF_TYPE"))
            n.typeSymbol = obj;


        if (n.children.size() != methodSym.symbols.size() + 1) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Method " + n.id + " of class " + objectType.getName() + " is applied to wrong number of arguments");
            return;
        }
        for (int i = 0; i < n.tokens.size(); ++i) {
            INode actual = n.children.get(i+1);
            IdSymbol formal = (IdSymbol) methodSym.symbols.values().toArray()[i];
//            if (actual.typeSymbol != formal.typeSymbol)
            if (!correctTypes(formal.typeSymbol, actual.typeSymbol)) {
                SymbolTable.error(n.ctx, n.tokens.get(i), "In call to method " + n.id + " of class " + objectType.getName() + ", actual type " + actual.typeSymbol.getName() + " of formal parameter " + formal.getName() + " is incompatible with declared type " + formal.typeSymbol.getName());
                return;
            }
        }
//        Symbol typeSymbol = SymbolTable.globals.lookup(n.type);
    }

    @Override
    public void visit(Local n) {
        if (n.id.id.equals("self")) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Let variable has illegal name self");
            return;
        }

        var sym = new IdSymbol(n.id.id);
        Symbol typeSymbol = SymbolTable.globals.lookup(n.type);

        if (typeSymbol == null) {
            SymbolTable.error(n.ctx, n.ctx.TYPE().getSymbol(), "Let variable " + n.id.id + " has undefined type " + n.type);
            return;
        }
        sym.typeSymbol = (TypeSymbol)typeSymbol;
        n.scope = currentScope;
        n.typeSymbol = sym.typeSymbol;

        currentScope = new DefaultScope(currentScope);
        n.scope = currentScope;
        if (n.expr != null) {
            n.expr.accept(this);

            TypeSymbol t2 = n.expr.typeSymbol;
            ClassSymbol classSym = SymbolTable.types.get(t2);

            if (classSym != null) //        FIXME
                if (classSym.lookupType(n.typeSymbol) == null)
                    SymbolTable.error(n.ctx, n.t, "Type " + t2.getName() + " of initialization expression of identifier " + n.id.id + " is incompatible with declared type " + n.typeSymbol);
        }

        currentScope.add(sym);
    }


    @Override
    public void visit(Let n) {
        Scope oldScope = currentScope;
        currentScope = new DefaultScope(currentScope);
        n.scope = currentScope;

        for (int i = 0; i < n.ctx.local().size(); ++i)
            n.children.get(i).accept(this);

        INode expr = n.children.get(n.children.size()-1);
        expr.accept(this);
        n.typeSymbol = expr.typeSymbol;
        currentScope = oldScope;
    }

    @Override
    public void visit(Id n) {
        Symbol symbol = currentScope.lookupVar(n.id);

        if (n.id.equals("self"))
            n.typeSymbol = (TypeSymbol) typeSelf;
        else if (symbol == null)
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Undefined identifier " + n.id);
        else
            n.typeSymbol = ((IdSymbol)symbol).typeSymbol;
    }

    @Override
    public void visit(Int n) {
        n.typeSymbol = (TypeSymbol) SymbolTable.globals.lookup("Int");
    }

    @Override
    public void visit(Bool n) {
        n.typeSymbol = (TypeSymbol) SymbolTable.globals.lookup("Bool");
    }

    @Override
    public void visit(StringNode n) {
        n.typeSymbol = (TypeSymbol) SymbolTable.globals.lookup("String");
    }

    @Override
    public void visit(NotBitwise n) {
        n.expr.accept(this);
        if (n.expr.typeSymbol != typeInt)
            SymbolTable.error(n.ctx, n.t1, "Operand of " + n.sym + " has type " + n.expr.typeSymbol + " instead of Int");
        n.typeSymbol = (TypeSymbol) typeInt;
    }

    @Override
    public void visit(NotBoolean n) {
        n.expr.accept(this);
        if (n.expr.typeSymbol != typeBool)
            SymbolTable.error(n.ctx, n.t1, "Operand of not has type " + n.expr.typeSymbol + " instead of Bool");
        n.typeSymbol = (TypeSymbol) typeBool;
    }

    @Override
    public void visit(Paranthesis n) {
        n.expr.accept(this);
        n.typeSymbol = n.expr.typeSymbol;
    }

    @Override
    public void visit(OpBinary n) {
        for (var c : n.children)
            c.accept(this);

        if (n.sym.equals("=")) {
            TypeSymbol typeString = (TypeSymbol) SymbolTable.globals.lookup("String");
            TypeSymbol t1 = n.children.get(0).typeSymbol;
            TypeSymbol t2 = n.children.get(1).typeSymbol;

            if ((t1 == typeInt || t1 == typeBool || t1 == typeString || t2 == typeInt || t2 == typeBool || t2 == typeString)
                    && t1 != t2)
                SymbolTable.error(n.ctx, n.op, "Cannot compare " + t1.getName() + " with " + t2.getName());
        }
        else if (n.children.get(0).typeSymbol != typeInt)
            SymbolTable.error(n.ctx, n.t1, "Operand of " + n.sym + " has type " + n.children.get(0).typeSymbol.getName() + " instead of Int");
        else if (n.children.get(1).typeSymbol != typeInt) {
            SymbolTable.error(n.ctx, n.t2, "Operand of " + n.sym + " has type " + n.children.get(1).typeSymbol.getName() + " instead of Int");
        }


        if (n.sym.equals("<") || n.sym.equals("<=") || n.sym.equals("="))
            n.typeSymbol = (TypeSymbol) typeBool;
        else
           n.typeSymbol = (TypeSymbol) typeInt;
    }

    @Override
    public void visit(Assign n) {
        IdSymbol s = (IdSymbol) currentScope.lookupVar(n.id.id);
        if (n.id.id.equals("self")) {
            SymbolTable.error(n.ctx, n.t1, "Cannot assign to self");
            return;
        } else if (s == null){
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Undefined identifier " + n.id);
            return;
        }

        ClassSymbol objClass = currentScope.getClassSym();
        TypeSymbol t1;
        if (s.typeSymbol.getName().equals("SELF_TYPE"))
            t1 = objClass.typeSymbol;
        else
            t1 = s.typeSymbol;
        n.expr.accept(this);
        TypeSymbol t2 = n.expr.typeSymbol;

        ClassSymbol classSym = SymbolTable.types.get(t2);
        if (classSym == null)
            return;

        if (!(s.typeSymbol == typeSelf && t2 == typeSelf) && classSym.lookupType(t1) == null) {
            SymbolTable.error(n.ctx, n.t2, "Type " + t2.getName() + " of assigned expression is incompatible with declared type " + s.typeSymbol.getName() + " of identifier " + n.id.id);
            return;
        }
//        if (t2.getName().equals("SELF_TYPE"))
//            t2 = objClass.typeSymbol;
        n.typeSymbol = t2;
    }

    @Override
    public void visit(New n) {
        TypeSymbol type = (TypeSymbol) SymbolTable.globals.lookup(n.type);
        if (type == null) {
            SymbolTable.error(n.ctx, n.t, "new is used with undefined type " + n.type);
            return;
        }
        n.typeSymbol = type;
    }

    @Override
    public void visit(IsVoid n) {
        n.expr.accept(this);
        n.typeSymbol = (TypeSymbol) typeBool;
    }

    @Override
    public void visit(While n) {
        n.children.get(0).accept(this);
        Symbol type = n.children.get(0).typeSymbol;
        if (type != typeBool)
            SymbolTable.error(n.ctx, n.t, "While condition has type " + type.getName() +" instead of Bool");
        n.typeSymbol = (TypeSymbol) typeObject;
    }

    @Override
    public void visit(If n) {
        for (var c : n.children)
            c.accept(this);
        Symbol type = n.children.get(0).typeSymbol;
        if (type != typeBool)
            SymbolTable.error(n.ctx, n.t, "If condition has type " + type.getName() +" instead of Bool");

        TypeSymbol t1 = n.children.get(1).typeSymbol;
        TypeSymbol t2 = n.children.get(2).typeSymbol;
        ClassSymbol classSym1 = SymbolTable.types.get(t1);
        ClassSymbol classSym2 = SymbolTable.types.get(t2);
//        self FIXME
        if (classSym1 == null)
            return;
        ClassSymbol common = classSym1.lca(classSym2);
//        self fixme
        if (common == null)
            return;
        n.typeSymbol = common.typeSymbol;
    }

    @Override
    public void visit(Case n) {
        for (int i = 1; i < n.children.size(); ++i)
            n.children.get(i).accept(this);
        TypeSymbol t = n.children.get(1).typeSymbol;
        ClassSymbol common = SymbolTable.types.get(t);

        for (int i = 2; i < n.children.size(); ++i) {
            t = n.children.get(i).typeSymbol;
            ClassSymbol classSym = SymbolTable.types.get(t);
            common = common.lca(classSym);
        }
        n.typeSymbol = common.typeSymbol;
    }

    @Override
    public void visit(CaseBranch n) {
        n.expr.accept(this);
        n.typeSymbol = n.expr.typeSymbol;
    }

    @Override
    public void visit(Block n) {
        Scope oldScope = currentScope;
        currentScope = new DefaultScope(currentScope);
        n.scope = currentScope;
        for (var c : n.children)
            c.accept(this);
        currentScope = oldScope;

        n.typeSymbol = n.children.get(n.children.size()-1).typeSymbol;
    }
}