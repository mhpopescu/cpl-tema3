package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class MethodDef extends INode {
    public List<INode> children;
    public String id, type;
    public CoolParser.Method_defContext ctx;
    public Token t;

    public MethodDef(CoolParser.Method_defContext ctx, Token t, List<INode> children_, String id_, String type_) {
        children = children_;
        id = id_;
        type = type_;
        this.ctx = ctx;
        this.t = t;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "method");
        System.out.println(offset + "  " + id);

        // first n-1 children are formal parameters, the last is method block
        int sz = children.size();
        for (int i = 0; i < sz - 1; ++i)
            children.remove(0).print(indentLvl + 1);

        System.out.println(offset + "  " + type);

        children.remove(0).print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
