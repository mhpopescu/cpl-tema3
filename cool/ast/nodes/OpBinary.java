package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class OpBinary extends INode {
    public List<INode> children;
    public CoolParser.ExprContext ctx;
    public Token op, t1, t2;
    public String sym;

    public OpBinary(Token op, Token t1, Token t2, CoolParser.ExprContext ctx, List<INode> children, String sym) {
        this.children = children;
        this.sym = sym;
        this.ctx = ctx;
        this.t1 = t1;
        this.t2 = t2;
        this.op = op;
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
