package cool.ast.visitor;

import cool.ast.nodes.ClassDef;
import cool.ast.nodes.Program;
import cool.structures.Scope;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public class CodeGenVisitor extends Visitor{
    public ST st;

    @Override
    public void visit(Program n) {
        for (var c : n.children)
            c.accept(this);
    }

/*    @Override
    public void visit(ClassDef n) {
        Scope oldScope = currentScope;
        currentScope = n.scope;

        for (var c : n.children)
            c.accept(this);

        currentScope = oldScope;
    }*/
}
