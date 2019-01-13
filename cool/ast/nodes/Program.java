package cool.ast.nodes;

import cool.ast.visitor.Visitor;

import java.util.List;

public class Program extends INode {
    public List<INode>  children;

    public Program(List<INode>  children_) {
        children = children_;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println("program");
        for (var x : children)
            x.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
