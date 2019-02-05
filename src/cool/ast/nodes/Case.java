package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

import java.util.List;

public class Case extends INode {
    public List<INode> children;
    String sym;
    public CoolParser.CaseContext ctx;

    public Case(CoolParser.CaseContext ctx, List<INode> children, String sym) {
        this.children = children;
        this.sym = sym;
        this.ctx = ctx;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + sym);
        for (var c : children)
            c.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
