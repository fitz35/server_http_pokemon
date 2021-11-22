package http.server.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ListPokemon {
    private final ArrayList<Pokemon> listOfPokemon;

    public ListPokemon(){
        this.listOfPokemon = new ArrayList<>();
        this.createListPokemon();
    }

    private void createListPokemon() {
        listOfPokemon.add(new Pokemon("Salameche",TypePokemon.FEU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Nidoqueen",TypePokemon.POISON,TypePokemon.SOL));
        listOfPokemon.add(new Pokemon("Arcanin",TypePokemon.FEU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Otaria",TypePokemon.EAU, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Osselait",TypePokemon.SOL, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Macronium",TypePokemon.PLANTE, TypePokemon.NOTYPE));
        listOfPokemon.add(new Pokemon("Méga-Pharamp",TypePokemon.DRAGON, TypePokemon.ELECTRIK));
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
                .getResourceAsStream("html/getAllPokemon.html");
        String s = new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));

        StringBuilder tabOfPokemon = new StringBuilder();

        for(Pokemon pokemon : this.listOfPokemon){
            if((name == null || name.compareTo(pokemon.getHtmlContent()) == 0) &&
                    (type1 == null || pokemon.isType(type1)) &&
                    (type2 == null || pokemon.isType(type2))){
                tabOfPokemon.append(pokemon.getHtmlContent());
            }
        }

        s = s.replace("%", tabOfPokemon.toString());

        return s;
    }

    public String getHtmlContent(String name, TypePokemon type1){
        return this.getHtmlContent(name, type1, null);
    }

    public String getHtmlContent(String name){
        return this.getHtmlContent(name, null, null);
    }

    public String getHtmlContent(){
        return this.getHtmlContent(null, null, null);
    }
}
