package cool.structures;

import cool.ast.nodes.INode;
import cool.structures.symbol.ClassSymbol;
import cool.structures.symbol.Symbol;

public interface Scope {
    public boolean add(Symbol sym);
    
    public Symbol lookup(String str);
    public Symbol lookupVar(String str);
    
    public Scope getParent();
    public ClassSymbol getClassSym();

    public void setParent(Scope s);
}
