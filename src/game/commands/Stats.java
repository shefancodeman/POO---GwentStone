package game.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.components.Games;

// clasa creata cu scopul de a include comenzile legate de win-urile
// fiecarui jucator / win-urile totale
public class Stats {
    // creem ObjectNode si scriem in el
    public static ObjectNode getTotalGamesPlayed () {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        print.put("command", "getTotalGamesPlayed");
        print.put("output", Games.playerOneWins + Games.playerTwoWins);
        return  print;
    }

    // la fel ca la prima clasa
    public static ObjectNode getPlayerOneWins () {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        print.put("command", "getPlayerOneWins");
        print.put("output", Games.playerOneWins);
        return  print;
    }

    public static ObjectNode getPlayerTwoWins () {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        print.put("command", "getPlayerTwoWins");
        print.put("output", Games.playerTwoWins);
        return  print;
    }
}
