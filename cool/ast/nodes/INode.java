package cool.ast.nodes;

import cool.ast.Visitable;
import cool.ast.visitor.Visitor;
import cool.structures.Scope;
import cool.structures.symbol.Symbol;
import cool.structures.symbol.TypeSymbol;

public abstract class INode implements Visitable {
    public Scope scope;
    public TypeSymbol typeSymbol;

    String getOffset(int indentLvl) {
        StringBuilder offset = new StringBuilder();
        for (int i = 0; i < indentLvl; i++)
            offset.append("  ");
        return offset.toString();
    }

    public abstract void print(int indentLvl);

    public void accept(Visitor v) {v.visit(this);};
}
