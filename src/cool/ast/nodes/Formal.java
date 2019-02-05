package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

public class Formal extends INode {
    public String id, type;
    public CoolParser.FormalContext ctx;

    public Formal(CoolParser.FormalContext ctx, String id_, String type_) {
        id = id_;
        type = type_;
        this.ctx = ctx;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "formal");
        System.out.println(offset + "  " + id);
        System.out.println(offset + "  " + type);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
