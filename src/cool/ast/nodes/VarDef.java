package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class VarDef extends INode {
    public INode expr;
    public String type;
    public Id id;
    public Token t;
    public CoolParser.Var_defContext ctx;

    public VarDef(CoolParser.Var_defContext ctx, Token t, INode expr, Id id, String type) {
        this.t = t;
        this.expr = expr;
        this.id = id;
        this.type = type;
        this.ctx = ctx;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "attribute");
        System.out.println(offset + "  " + id);
        System.out.println(offset + "  " + type);
        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
