package game.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.GameInput;
import game.cards.Card;
import game.components.Deck;
import game.heroes.Hero;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
public class Games {
    private  ArrayList<Deck> p1Decks;
    private  ArrayList<Deck> p2Decks;
    private static int playerOneWins;
    private static int playerTwoWins;
    private ArrayList<GameInput> gamesList;

    // initializam jocul cu win si games countere
    // cat si deckurile din care putem alege
    public Games(final ArrayList<Deck> playerOneDecks,
                      final ArrayList<Deck> playerTwoDecks,
                      final ArrayList<GameInput> gamesList) {
        this.p1Decks = playerOneDecks;
        this.p2Decks= playerTwoDecks;
        this.gamesList = gamesList;
        playerOneWins = 0;
        playerTwoWins = 0;
    }

    public ArrayNode startMatches() {
        Match match;
        // creem node-ul de output
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode output = objectMapper.createArrayNode();

        // incepem sa jucam meci cu meci, adaugand la input output-ul fiecare comenzi
        for (GameInput game : gamesList) {
            match = new Match(output, game, p1Decks, p2Decks);
            for (ActionsInput action: game.getActions()) {
                // rulam comanda cu comanda
                    ObjectNode temp = match.play(action);
                    if (temp != null) {
                        output.add(temp);
                    }
            }
        }
        return output;
    }
}
