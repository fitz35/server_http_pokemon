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
        listOfPokemon.add(new Pokemon("Salameche",TypePokemon.FEU,null));
        listOfPokemon.add(new Pokemon("Nidoqueen",TypePokemon.POISON,TypePokemon.SOL));
        listOfPokemon.add(new Pokemon("Arcanin",TypePokemon.FEU,null));
        listOfPokemon.add(new Pokemon("Otaria",TypePokemon.EAU,null));
        listOfPokemon.add(new Pokemon("Osselait",TypePokemon.SOL,null));
        listOfPokemon.add(new Pokemon("Macronium",TypePokemon.PLANTE,null));
        listOfPokemon.add(new Pokemon("Méga-Pharamp",TypePokemon.DRAGON,TypePokemon.ELECTRIK));
        listOfPokemon.add(new Pokemon("Jungko",TypePokemon.PLANTE,null));
        listOfPokemon.add(new Pokemon("Florges",TypePokemon.FEE,null));
    }

    /**
     * get the html representation of the list of pokemon
     * @return the html representation of the list of pokemon
     */
    public String getHtmlContent(){
        InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("html/getAllPokemon.html");
        String s = new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n"));

        StringBuilder tabOfPokemon = new StringBuilder();

        for(Pokemon pokemon : this.listOfPokemon){
            tabOfPokemon.append("<tr>");
            tabOfPokemon.append("<td>").append(pokemon.getName()).append("</td>");
            tabOfPokemon.append("<td>").append(pokemon.getType1()).append("</td>");
            tabOfPokemon.append("<td>").append(pokemon.getType2() == null ? "" : pokemon.getType2()).append("</td>");
            tabOfPokemon.append("/<tr>");
        }

        s = s.replace("%", tabOfPokemon.toString());

        return s;
    }
}
