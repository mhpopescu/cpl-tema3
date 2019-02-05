package cool.ast.nodes;

import cool.ast.visitor.Visitor;

public class Int extends INode {
    int val;

    public Int(int val) {
        this.val = val;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + val);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}