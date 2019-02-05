package cool.structures.symbol;

import cool.structures.DefaultScope;
import cool.structures.Scope;
import cool.structures.SymbolTable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClassSymbol extends MethodSymbol {
    public DefaultScope vars;

    public ClassSymbol(String name, Scope parent) {
        super(name, parent);
        vars = new DefaultScope(parent);
    }

    public boolean addVar(Symbol sym) {
        return vars.add(sym);
    }

    @Override
    public Symbol lookupVar(String s) {
        var sym = vars.lookup(s);

        if (sym != null)
            return sym;

        // Dacă nu găsim simbolul în domeniul de vizibilitate curent, îl căutăm
        // în domeniul de deasupra.
        if (parent != null && parent != SymbolTable.globals)
            return parent.lookupVar(s);

        return null;
    }

    public ClassSymbol lookupType(TypeSymbol t) {
        if (typeSymbol == t)
            return this;
        // Il cautam in parinti
        if (parent != null && parent != SymbolTable.globals)
            return ((ClassSymbol)parent).lookupType(t);
        return null;
    }

    public ClassSymbol lca(ClassSymbol classSym2) {
//        if (this == classSym2)
//            return this;
        LinkedList<ClassSymbol> parents = new LinkedList<>();

        for (ClassSymbol n = this; n != null && n.parent != SymbolTable.globals; n = (ClassSymbol) n.parent)
            parents.addLast(n);
        ClassSymbol current = classSym2;
        while (current != null && current.parent != SymbolTable.globals && !parents.contains(current))
            current = (ClassSymbol)current.parent;
        return current;
    }

    @Override
    public ClassSymbol getClassSym() {
        return this;
    }

    public List<String> getAllMethods() {
        List<String> ret;

        if (parent == null || parent == SymbolTable.globals)
            ret = new ArrayList<>();
        else
            ret = ((ClassSymbol)parent).getAllMethods();

        for (var x : symbols.keySet())
            ret.add(getName() + "." + x);
        return ret;
    }
}
