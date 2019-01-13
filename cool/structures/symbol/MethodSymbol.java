package cool.structures.symbol;

import cool.structures.Scope;
import cool.structures.SymbolTable;

import java.util.LinkedHashMap;
import java.util.Map;

// O metodă este atât simbol, cât și domeniu de vizibilitate pentru parametrii
// săi formali.
public class MethodSymbol extends IdSymbol implements Scope {
    public Map<String, Symbol> symbols = new LinkedHashMap<>();

    public Scope parent;

    public MethodSymbol(String name, Scope parent) {
        super(name);
        this.parent = parent;
    }

    @Override
    public boolean add(Symbol sym) {
        if (symbols.containsKey(sym.getName()))
            return false;

        symbols.put(sym.getName(), sym);

        return true;
    }

    @Override
    public Symbol lookup(String s) {
        var sym = symbols.get(s);

        if (sym != null)
            return sym;

        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null && parent != SymbolTable.globals)
            return parent.lookup(s);

        return null;
    }

    @Override
    public Symbol lookupVar(String s) {
        var sym = symbols.get(s);
        if (sym != null)
            return sym;

        if (parent != null && parent != SymbolTable.globals)
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
}
