package http.server.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setType1(TypePokemon type1) {
        Type1 = type1;
    }

    public void setType2(TypePokemon type2) {
        Type2 = type2;
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

    public byte[] getPokemonPicture() throws IOException {
        String pictureName= "images/"+this.name+ ".png";
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(pictureName);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[6000000];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
