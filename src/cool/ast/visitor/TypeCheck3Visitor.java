package cool.ast.visitor;

import cool.ast.nodes.*;
import cool.structures.DefaultScope;
import cool.structures.Scope;
import cool.structures.SymbolTable;
import cool.structures.symbol.ClassSymbol;
import cool.structures.symbol.IdSymbol;
import cool.structures.symbol.MethodSymbol;
import cool.structures.symbol.Symbol;

public class TypeCheck3Visitor extends Visitor{
    private Scope currentScope = SymbolTable.globals;

    @Override
    public void visit(Program n) {
        for (var c : n.children)
            c.accept(this);
    }

    @Override
    public void visit(ClassDef n) {
        if (n.hasInheritance()) {
            Scope oldScope = currentScope;
            currentScope = n.scope;

            for (var c : n.children)
                c.accept(this);

            currentScope = oldScope;
        }
    }

    @Override
    public void visit(VarDef n) {
        if (((ClassSymbol)currentScope.getParent()).vars.lookup(n.id.id) != null)
            SymbolTable.error(n.ctx, n.ctx.idName, "Class " + ((ClassSymbol)currentScope).getName() + " redefines inherited attribute " + n.id.id);

    }

    @Override
    public void visit(MethodDef n) {
        MethodSymbol baseFuncSym = (MethodSymbol)currentScope.getParent().lookup(n.id);
        MethodDef baseFuncNode = (MethodDef) SymbolTable.symbolsNode.get(baseFuncSym);

        if (baseFuncNode == null)
            return;

        if (n.children.size() != baseFuncNode.children.size()) {
            SymbolTable.error(n.ctx, n.ctx.ID().getSymbol(), "Class " + ((ClassSymbol) currentScope).getName() + " overrides method " + n.id + " with different number of formal parameters");
            return;
        }

        for (int i = 0; i < n.children.size() - 1; ++i) {
            Formal f1 = (Formal) n.children.get(i);
            Formal f2 = (Formal) baseFuncNode.children.get(i);
            if (!f1.type.equals(f2.type)) {
                SymbolTable.error(n.ctx, f1.ctx.typeName, "Class " + ((ClassSymbol) currentScope).getName() + " overrides method " + n.id + " but changes type of formal parameter " + f1.id + " from " + f2.type + " to " + f1.type);
                return;
            }
        }
        if (!n.type.equals(baseFuncNode.type))
            SymbolTable.error(n.ctx, n.ctx.TYPE().getSymbol(), "Class " + ((ClassSymbol) currentScope).getName() + " overrides method " + n.id + " but changes return type from " + baseFuncNode.type + " to " + n.type);
    }
}