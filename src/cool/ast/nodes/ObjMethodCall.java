package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.LinkedList;
import java.util.List;

public class ObjMethodCall extends INode {
    public List<INode> children;
    public String type, id;
    public CoolParser.Obj_method_callContext ctx;
    public Token t;
    public LinkedList<Token> tokens;

    public ObjMethodCall(CoolParser.Obj_method_callContext ctx, LinkedList<Token> tokens, List<INode> children, String type, String id) {
        this.id = id;
        this.type = type;
        this.children = children;
        this.ctx = ctx;
        this.tokens = tokens;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + ".");

        children.remove(0).print(indentLvl + 1);

        if (type != null)
            System.out.println(offset + "  " + type);
        System.out.println(offset + "  " + id);


        for (var c : children)
            c.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
