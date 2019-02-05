package cool.ast.visitor;

import cool.ast.nodes.*;
import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AstBuilder extends CoolParserBaseVisitor<INode> {
    // return list of children
    List<INode> visitAll(ParserRuleContext ctx) {
        List<INode> children = new ArrayList<>();
        for (var x : ctx.children) {
            INode n = visit(x);
            if (n != null)
                children.add(n);
        }
        return children;
    }

    @Override
    public INode visitProgram(CoolParser.ProgramContext ctx) {
        return new Program(visitAll(ctx));
    }

    @Override
    public ClassDef visitClass_def(CoolParser.Class_defContext ctx) {
        List<INode> children = visitAll(ctx);
        String baseClass = null;
        if (ctx.baseClass != null)
            baseClass = ctx.baseClass.getText();
        return new ClassDef(ctx, children, ctx.className.getText(), baseClass);
    }

    @Override
    public INode visitMethod_def(CoolParser.Method_defContext ctx) {
        return new MethodDef(ctx, ctx.exp.start, visitAll(ctx), ctx.ID().getText(), ctx.TYPE().getText());
    }

    @Override
    public INode visitVar_def(CoolParser.Var_defContext ctx) {
        if (ctx.expr() != null)
            return new VarDef(ctx, ctx.idName, visit(ctx.expr()), new Id(ctx.ID().getText()), ctx.typeName.getText());
        else
            return new VarDef(ctx, ctx.idName, null, new Id(ctx.ID().getText()), ctx.typeName.getText());
    }

    @Override
    public INode visitFormal(CoolParser.FormalContext ctx) {
        return new Formal(ctx, ctx.ID().getText(), ctx.TYPE().getText());
    }

    @Override
    public INode visitLocal(CoolParser.LocalContext ctx) {
        if (ctx.expr() != null)
            return new Local(ctx, ctx.expr().getStart(), visit(ctx.expr()), new Id(ctx.ID().getText()), ctx.TYPE().getText());
        return new Local(ctx, null, null, new Id(ctx.ID().getText()), ctx.TYPE().getText());

    }

    @Override
    public INode visitInt(CoolParser.IntContext ctx) {
        return new Int(Integer.parseInt(ctx.getText()));
    }

    @Override
    public INode visitBool(CoolParser.BoolContext ctx) {
        return new Bool(ctx.getText());
    }

    @Override
    public INode visitString(CoolParser.StringContext ctx) {
        return new StringNode(ctx.getText());
    }

    @Override
    public INode visitMultiply(CoolParser.MultiplyContext ctx) {
        return new OpBinary(ctx.MULTIPLY().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.MULTIPLY().getText());
    }

    @Override
    public INode visitDivide(CoolParser.DivideContext ctx) {
        return new OpBinary(ctx.DIVIDE().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.DIVIDE().getText());
    }

    @Override
    public INode visitPlus(CoolParser.PlusContext ctx) {
        return new OpBinary(ctx.PLUS().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.PLUS().getText());
    }

    @Override
    public INode visitMinus(CoolParser.MinusContext ctx) {
        return new OpBinary(ctx.MINUS().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.MINUS().getText());
    }

    @Override
    public INode visitLess_eq(CoolParser.Less_eqContext ctx) {
        return new OpBinary(ctx.LESS_EQ().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.LESS_EQ().getText());
    }

    @Override
    public INode visitLess(CoolParser.LessContext ctx) {
        return new OpBinary(ctx.LESS().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.LESS().getText());
    }

    @Override
    public INode visitEqual(CoolParser.EqualContext ctx) {
        return new OpBinary(ctx.EQUAL().getSymbol(), ctx.expr(0).start, ctx.expr(1).start, ctx, visitAll(ctx), ctx.EQUAL().getText());
    }

    @Override
    public INode visitNot_bitwise(CoolParser.Not_bitwiseContext ctx) {
        return new NotBitwise(ctx.expr().start, ctx, visit(ctx.expr()), ctx.NOT_BITWISE().getText());
    }

    @Override
    public INode visitNot_boolean(CoolParser.Not_booleanContext ctx) {
        return new NotBoolean(ctx.expr().start, ctx, visit(ctx.expr()), ctx.NOT_BOOLEAN().getText());
    }

    @Override
    public INode visitAssign(CoolParser.AssignContext ctx) {
        return new Assign(ctx.ID().getSymbol(), ctx.expr().getStart(), ctx, visit(ctx.expr()), new Id(ctx.ID().getText()), ctx.ASSIGN().getText());
    }

    @Override
    public INode visitParanthesis(CoolParser.ParanthesisContext ctx) {
        return new Paranthesis(ctx, visit(ctx.expr()));
    }

    @Override
    public INode visitNew(CoolParser.NewContext ctx) {
        return new New(ctx, ctx.TYPE().getSymbol(), ctx.NEW().getText(), ctx.TYPE().getText());
    }

    @Override
    public INode visitIs_void(CoolParser.Is_voidContext ctx) {
        return new IsVoid(visit(ctx.expr()), ctx.IS_VOID().getText());
    }

    @Override
    public INode visitObj_method_call(CoolParser.Obj_method_callContext ctx) {
        String type = null;
        var ctxType = ctx.TYPE();
        if (ctx.TYPE() != null)
            type = ctxType.getText();
        LinkedList<Token> tokens = new LinkedList<>();
        for (int i = 1; i < ctx.expr().size(); ++i)
            tokens.addLast(ctx.expr(i).start);
//        Token ctxTypeToken = null;
//        if (ctxType != null)
//            ctxTypeToken = ctxType.getSymbol();
        return new ObjMethodCall(ctx, tokens, visitAll(ctx), type, ctx.ID().getText());
    }

    @Override
    public INode visitMethod_call(CoolParser.Method_callContext ctx) {
        LinkedList<Token> tokens = new LinkedList<>();
        for (int i = 0; i < ctx.expr().size(); ++i)
            tokens.addLast(ctx.expr(i).start);
        return new MethodCall(ctx, tokens, visitAll(ctx), ctx.ID().getText());
    }

    @Override
    public INode visitIf(CoolParser.IfContext ctx) {
        return new If(ctx, ctx.expr(0).getStart(),visitAll(ctx));
    }

    @Override
    public INode visitWhile(CoolParser.WhileContext ctx) {
        return new While(ctx, ctx.expr(0).getStart(), visitAll(ctx));
    }

    @Override
    public INode visitLet(CoolParser.LetContext ctx) {
        return new Let(ctx, visitAll(ctx));
    }

    @Override
    public INode visitCase(CoolParser.CaseContext ctx) {
        return new Case(ctx, visitAll(ctx), ctx.CASE().getText());
    }

    @Override
    public INode visitBlock(CoolParser.BlockContext ctx) {
        return new Block(visitAll(ctx));
    }

    @Override
    public INode visitId(CoolParser.IdContext ctx) {
        return new Id(ctx, ctx.ID().getText());
    }

    @Override
    public INode visitCase_branch(CoolParser.Case_branchContext ctx) {
        return new CaseBranch(visit(ctx.expr()), ctx.ID().getText(), ctx.TYPE().getText());
    }
}
