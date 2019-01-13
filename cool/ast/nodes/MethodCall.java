package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.LinkedList;
import java.util.List;

public class MethodCall extends INode {
    public List<INode> children;
    public String id;
    public CoolParser.Method_callContext ctx;
    public LinkedList<Token> tokens;

    public MethodCall(CoolParser.Method_callContext ctx, LinkedList<Token> tokens, List<INode> children, String id) {
        this.id = id;
        this.children = children;
        this.ctx = ctx;
        this.tokens = tokens;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "implicit dispatch");
        System.out.println(offset + "  " + id);
        for (var c : children)
            c.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
