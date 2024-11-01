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
    private  ArrayList<ArrayList<CardInput>> p1Decks;
    private  ArrayList<ArrayList<CardInput>> p2Decks;
    // static pentru a putea fi accesate din afara clasei
    public static int playerOneWins;
    public static int playerTwoWins;
    private ArrayList<GameInput> gamesList;

    // initializam jocul cu win si games counters
    // cat si deckurile din care putem alege, jocul constand din
    // mai multe meciuri legate impreuna
    public Games(final ArrayList<ArrayList<CardInput>> playerOneDecks,
                      final ArrayList<ArrayList<CardInput>> playerTwoDecks,
                      final ArrayList<GameInput> gamesList) {
        // creem deck-uri noi din deckInput,
        // din moment ce trebuie sa-si revina la starea initiala
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

        // creem un nou meci, augand la input deck-urile.
        // facem asta inainte de fiecare meci pentru a mentine
        // ordinea cartilor si numarul lor in deck
        for (GameInput game : gamesList) {
            // initializam deck-urile playerilor in ArrayList<Deck>
            ArrayList<ArrayList<CardInput>> temp1 =  p1Decks;
            ArrayList<Deck> deckListOne = new ArrayList<>();
            int i = 0;
            for (ArrayList<CardInput> deck: temp1) {
                deckListOne.add(new Deck(deck, i, 1));
                i++;
            }
            i = 0;
            ArrayList<ArrayList<CardInput>> temp2 =  p2Decks;
            ArrayList<Deck> deckListTwo = new ArrayList<>();
            for (ArrayList<CardInput> deck: temp2) {
                deckListTwo.add(new Deck(deck, i, 2));
                i++;
            }
            match = new Match(output, game, deckListOne, deckListTwo);
            for (ActionsInput action: game.getActions()) {
                // rulam comanda cu comanda
                    ObjectNode temp = match.play(action);
                    if (temp != null) {
                        output.add(temp);
                    }
            }
            if (match.winner == 1) {
                playerOneWins++;
            } else if (match.winner == 2) {
                playerTwoWins++;
            }
        }
        return output;
    }
}
