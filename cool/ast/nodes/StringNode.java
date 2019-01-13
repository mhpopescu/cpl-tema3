package cool.ast.nodes;

import cool.ast.visitor.Visitor;

public class StringNode extends INode {
    String val;

    public StringNode(String val) {
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