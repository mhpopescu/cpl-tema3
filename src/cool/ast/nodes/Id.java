package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

public class Id extends INode {
    public String id;
    public CoolParser.IdContext ctx;

    public Id(CoolParser.IdContext ctx, String id) {
        this.id = id;
        this.ctx = ctx;
    }

    public Id(String id) {
        this.id = id;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + id);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
