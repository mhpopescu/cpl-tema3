package cool.ast;

import cool.ast.visitor.Visitor;

public interface Visitable {
    public void accept(Visitor v);
}
