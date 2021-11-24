package http.server.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *  Class that manages the list of Pokemon used in the application
 *  @author Tushita Ramkaran and Clement Lahoche
 *   @version 1.0
 */

public class ListPokemon {
    private final ArrayList<Pokemon> listOfPokemon;

    public ListPokemon(){
        this.listOfPokemon = new ArrayList<>();
        this.createListPokemon();
    }

    /**
     * Fills the list with relevant Pokemon data
     */
    public void createListPokemon() {
        listOfPokemon.add(new Pokemon("Salameche",TypePokemon.FEU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Nidoqueen",TypePokemon.POISON,TypePokemon.SOL));
        listOfPokemon.add(new Pokemon("Arcanin",TypePokemon.FEU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Otaria",TypePokemon.EAU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Osselait",TypePokemon.SOL, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Macronium",TypePokemon.PLANTE, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Mega-Pharamp",TypePokemon.DRAGON, TypePokemon.ELECTRIK));
        listOfPokemon.add(new Pokemon("Jungko",TypePokemon.PLANTE, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Florges",TypePokemon.FEE, TypePokemon.NOTYPE));
    }

    /**
     * get the html representation of the list of pokemon with filter
     * @param name filter on name
     * @param type1 filter on first type
     * @param type2 filter on second type
     * @return the html representation of the list of pokemon
     */
    public String getHtmlContent(String name, TypePokemon type1, TypePokemon type2){
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("html_template/getAllPokemon.html");
        String s = new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));

        StringBuilder tabOfPokemon = new StringBuilder();

        for(Pokemon pokemon : this.listOfPokemon){
            if((name == null || name.compareTo(pokemon.getName()) == 0) &&
                    (type1 == null || pokemon.isType(type1)) &&
                    (type2 == null || pokemon.isType(type2))){
                tabOfPokemon.append(pokemon.getHtmlContent());
                System.out.println(pokemon.getHtmlContent());
            }
        }

        String tabFormatted = tabOfPokemon.toString();

        if(tabFormatted.compareTo("") == 0){
            tabFormatted = "<tr>" + "<td colspan=\"3\">" + "No pokemon found !" + "</td>" + "</tr>";
        }

        s = s.replace("%", tabFormatted);

        StringBuilder selectTypeBuilder = new StringBuilder();

        for(TypePokemon type: TypePokemon.values()){
            String toDisplay = type.toString();
            if(type == TypePokemon.NOTYPE){
                toDisplay = "NO TYPE";
            }
            selectTypeBuilder.append("<option value=\"").append(type).append("\">").append(toDisplay).append("</option>");
        }

        s = s.replace("/type/", selectTypeBuilder.toString());

        return s;
    }

    /**
     * get the html representation of the list of pokemon with filter
     * @param name filter on name
     * @param type1 filter on first type
     * @return the html representation of the list of pokemon
     */
    public String getHtmlContent(String name, TypePokemon type1){
        return this.getHtmlContent(name, type1, null);
    }

    /**
     * get the html representation of the list of pokemon with filter
     * @param name filter on name
     * @return the html representation of the list of pokemon
     */
    public String getHtmlContent(String name){
        return this.getHtmlContent(name, null, null);
    }

    /**
     * get the html representation of the list of pokemon with no filter
     * @return the html representation of the list of pokemon
     */
    public String getHtmlContent(){
        return this.getHtmlContent(null, null, null);
    }

    /**
     * Adds a new Pokemon to the existing list of pokemon
     * @param newPokemon the pokemon to be added to the list
     */
    public void addPokemon(Pokemon newPokemon) {listOfPokemon.add(newPokemon);}

    public boolean removePokemon(String pokemonName)
    {
        for(Pokemon pokemon: listOfPokemon)
        {
            if(pokemon.getName().compareTo(pokemonName)==0)
            {
                listOfPokemon.remove(pokemon);
                return true;
            }
        }
        return false;
    }

    /**
     * Return the pokemon having the name specified
     * @param name the name of the pokemon
     * @return
     */
    public Pokemon getPokemonByName(String name) {
        for (Pokemon pokemon : listOfPokemon) {
            if (pokemon.getName().compareTo(name) == 0) {
                return pokemon;
            }
        }
        return null;
    }
}
