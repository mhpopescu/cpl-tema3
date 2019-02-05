package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

import java.util.List;

public class Let extends INode {
    public List<INode> children;
    public CoolParser.LetContext ctx;

    public Let(CoolParser.LetContext ctx, List<INode> children) {
        this.children = children;
        this.ctx = ctx;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + "let");
        for (var e : children)
            e.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
