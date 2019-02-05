package cool.ast.nodes;

import cool.ast.visitor.Visitor;

public class IsVoid extends INode {
    public INode expr;
    String sym;

    public IsVoid(INode expr, String sym) {
        this.expr = expr;
        this.sym = sym;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + sym);
        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
