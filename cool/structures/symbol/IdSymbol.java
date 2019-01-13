package cool.structures.symbol;

public class IdSymbol extends Symbol {
    public TypeSymbol typeSymbol = null;

    public IdSymbol(String name) {
        super(name);
    }

    public void setTypeSymbol(TypeSymbol type) {
        this.typeSymbol = type;
    }

    public TypeSymbol getTypeSymbol() {
        return typeSymbol;
    }
}
