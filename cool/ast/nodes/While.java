package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class While extends INode {
    public List<INode> children;
    public CoolParser.WhileContext ctx;
    public Token t;

    public While(CoolParser.WhileContext ctx, Token t, List<INode> children) {
        this.children = children;
        this.ctx = ctx;
        this.t = t;
    }

    @Override
    public void print(int indentLvl) {
        System.out.println(getOffset(indentLvl) + "while");
        for (var e : children)
            e.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
