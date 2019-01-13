package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

public class New extends INode {
    public String sym, type;
    public CoolParser.NewContext ctx;
    public Token t;

    public New(CoolParser.NewContext ctx, Token t, String sym, String type) {
        this.sym = sym;
        this.type = type;
        this.ctx = ctx;
        this.t = t;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + sym);
        System.out.println(offset + "  " + type);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
