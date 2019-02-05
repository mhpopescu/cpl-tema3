package cool.ast.nodes;

import cool.ast.visitor.Visitor;

import java.util.List;

public class CaseBranch extends INode {
    public INode expr;
    String id, type;

    public CaseBranch(INode expr, String id, String type) {
        this.expr = expr;
        this.id = id;
        this.type = type;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "case branch");
        System.out.println(offset + "  " + id);
        System.out.println(offset + "  " + type);

        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
