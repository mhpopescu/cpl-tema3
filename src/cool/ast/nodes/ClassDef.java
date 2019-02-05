package cool.ast.nodes;

import cool.ast.visitor.Visitor;
import cool.parser.CoolParser;

import java.util.List;

public class ClassDef extends INode {
    public String id1, id2;
    public List<INode> children;
    public CoolParser.Class_defContext ctx;
    public ClassDef baseClass = null;

    public ClassDef(CoolParser.Class_defContext ctx, List<INode>  children_, String id1_, String id2_) {
        id1 = id1_;
        id2 = id2_;
        children = children_;
        this.ctx = ctx;
    }

    public void print(int indentLvl) {
        String offset = getOffset(indentLvl);
        System.out.println(offset + "class");
        System.out.println(offset + "  " + id1);
        if (id2 != null)
            System.out.println(offset + "  " + id2);

        for (var c : children)
            c.print(indentLvl + 1);
    }

    public Boolean hasInheritance() {
        return id2 != null;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
