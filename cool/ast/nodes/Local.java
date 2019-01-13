package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;
import cool.structures.symbol.IdSymbol;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class Local extends INode {
    public CoolParser.LocalContext ctx;
    public  INode expr;
    public String type;
    public Id id;
    public Token t;

    public Local(CoolParser.LocalContext ctx, Token t, INode expr, Id id_, String type_) {
        this.expr = expr;
        id = id_;
        type = type_;
        this.ctx = ctx;
        this.t = t;
    }

    @Override
    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "local");
        System.out.println(offset + "  " + id);
        id.print(indentLvl + 1);
        System.out.println(offset + "  " + type);
        expr.print(indentLvl + 1);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
