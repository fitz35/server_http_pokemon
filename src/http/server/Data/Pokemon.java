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

    /**
     * test if this pokemon is the type type
     * @param type the type
     * @return if this pokemon is the type type
     */
    public boolean isType(TypePokemon type){
        return this.Type1 == type || this.Type2 == type;
    }

    public String getHtmlContent(){
        return "<tr>" +
                "<td>" + this.getName() + "</td>" +
                "<td>" + this.getType1() + "</td>" +
                "<td>" + (this.getType2() == TypePokemon.NOTYPE ? "" : this.getType2()) + "</td>" +
                "</tr>";
    }
}
