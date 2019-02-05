package cool.ast.nodes;

import cool.ast.visitor.Visitor;

public class Bool extends INode {
    public String val;

    public Bool(String val) {
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