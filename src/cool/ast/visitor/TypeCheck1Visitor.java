package cool.ast.visitor;

import cool.ast.nodes.ClassDef;
import cool.ast.nodes.Program;
import cool.structures.SymbolTable;
import cool.structures.symbol.ClassSymbol;
import cool.structures.symbol.TypeSymbol;

public class TypeCheck1Visitor extends Visitor{
    @Override
    public void visit(Program n) {
        for (var c : n.children)
            c.accept(this);
    }

    @Override
    public void visit(ClassDef n) {
        // La definirea unei noi clase, creăm un nou simbol.
        var sym = new ClassSymbol(n.id1, SymbolTable.object);
        sym.typeSymbol = new TypeSymbol(n.id1);

        // Adăugăm simbolul în domeniul de vizibilitatea curent,
        // semnalând eroare dacă exista deja acolo.
        if (n.id1.equals("SELF_TYPE")) {
            SymbolTable.error(n.ctx, n.ctx.className, "Class has illegal name SELF_TYPE");
            return;
        }
        if (!SymbolTable.globals.add(sym.typeSymbol)) {
            SymbolTable.error(n.ctx, n.ctx.className, "Class " + n.id1 + " is redefined");
            return;
        }

        SymbolTable.symbolsNode.put(sym.typeSymbol, n);
        SymbolTable.types.put(sym.typeSymbol, sym);
        n.scope = sym;
    }
}
