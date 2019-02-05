package cool.structures;

import cool.structures.symbol.ClassSymbol;
import cool.structures.symbol.Symbol;

import java.util.*;

public class DefaultScope implements Scope {
    
    private Map<String, Symbol> symbols = new LinkedHashMap<>();
    
    private Scope parent;
    
    public DefaultScope(Scope parent) {
        this.parent = parent;
    }

    @Override
    public boolean add(Symbol sym) {
        // Reject duplicates in the same scope.
        if (symbols.containsKey(sym.getName()))
            return false;
        
        symbols.put(sym.getName(), sym);
        
        return true;
    }

    @Override
    public Symbol lookup(String name) {
        var sym = symbols.get(name);
        
        if (sym != null)
            return sym;
        
        if (parent != null)
            return parent.lookup(name);
        
        return null;
    }

    @Override
    public Symbol lookupVar(String s) {
        var sym = symbols.get(s);

        if (sym != null)
            return sym;

        if (parent != null)
            return parent.lookupVar(s);

        return null;
    }

    @Override
    public Scope getParent() {
        return parent;
    }

    @Override
    public ClassSymbol getClassSym() {
        return parent.getClassSym();
    }

    @Override
    public void setParent(Scope s) {
        this.parent = s;
    }

    @Override
    public String toString() {
        return symbols.values().toString();
    }

}
