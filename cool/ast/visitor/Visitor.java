package cool.ast.visitor;

import cool.ast.nodes.*;

public abstract class Visitor {
    public void visit(INode n) {}
    public void visit(Program n) {}
    public void visit(ClassDef n) {}
    public void visit(VarDef n) {}
    public void visit(MethodDef n) {}
    public void visit(Formal n) {}
    public void visit(Local n) {}
    public void visit(Let n) {}
    public void visit(CaseBranch n) {}
    public void visit(Case n) {}
    public void visit(Id n) {}
    public void visit(Int n) {}
    public void visit(Bool n) {}
    public void visit(StringNode n) {}
    public void visit(OpBinary n) {}
    public void visit(NotBitwise n) {}
    public void visit(NotBoolean n) {}
    public void visit(Paranthesis n) {}
    public void visit(Assign n) {}
    public void visit(New n) {}
    public void visit(IsVoid n) {}
    public void visit(While n) {}
    public void visit(If n) {}
    public void visit(Block n) {}
    public void visit(MethodCall n) {}
    public void visit(ObjMethodCall n) {}


//    void visit(CaseBranch n);

}
