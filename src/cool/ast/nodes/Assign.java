package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class Assign extends INode {
    public INode expr;
    public Id id;
    String sym;
    public CoolParser.AssignContext ctx;
    public Token t1, t2;

    public Assign(Token t1, Token t2, CoolParser.AssignContext ctx, INode expr, Id id, String sym) {
        this.expr = expr;
        this.id = id;
        this.sym = sym;
        this.ctx = ctx;
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + sym);
        id.print(indentLvl + 1);
        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
