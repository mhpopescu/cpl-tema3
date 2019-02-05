package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

public class NotBitwise extends INode {
    public CoolParser.Not_bitwiseContext ctx;
    public INode expr;
    public String sym;
    public Token t1;

    public NotBitwise(Token t1, CoolParser.Not_bitwiseContext ctx, INode expr, String sym) {
        this.expr = expr;
        this.sym = sym;
        this.ctx = ctx;
        this.t1 = t1;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + sym);
        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
