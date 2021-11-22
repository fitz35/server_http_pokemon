package http.server.Data;

public class Pokemon
{
    private String name;
    private TypePokemon Type1;
    private TypePokemon Type2;

    public Pokemon(String name, TypePokemon type1, TypePokemon type2)
    {
        this.name = name;
        Type1 = type1;
        Type2 = type2;
    }

    public String getName() {
        return name;
    }

    public TypePokemon getType1() {
        return Type1;
    }

    public TypePokemon getType2() {
        return Type2;
    }
}
