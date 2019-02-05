package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

import java.util.List;

public class Paranthesis extends INode {
    public CoolParser.ParanthesisContext ctx;
    public INode expr;

    public Paranthesis(CoolParser.ParanthesisContext ctx, INode expr) {
        this.expr = expr;
        this.ctx = ctx;
    }

    @Override
    public void print(int indentLvl) {
        expr.print(indentLvl);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
