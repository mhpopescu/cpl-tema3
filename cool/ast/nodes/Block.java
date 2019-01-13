package cool.ast.nodes;

import cool.ast.visitor.Visitor;

import java.util.List;

public class Block extends INode {
    public List<INode> children;

    public Block(List<INode> children) {
        this.children = children;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + "block");
        for (var e : children)
            e.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
