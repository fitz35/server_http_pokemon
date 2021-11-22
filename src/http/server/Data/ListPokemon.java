package http.server.Data;

import java.util.ArrayList;

public class ListPokemon {
    ArrayList<Pokemon> listOfPokemon;


    public void createListPokemon() {
        listOfPokemon = new ArrayList<Pokemon>();
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
}
