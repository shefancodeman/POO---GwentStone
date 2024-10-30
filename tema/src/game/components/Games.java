package game.components;

import fileio.GameInput;
import game.components.Deck;

import java.util.ArrayList;

public class Games {
    private  ArrayList<Deck> p1Decks;
    private  ArrayList<Deck> p2Decks;
    private static int playerOneWins;
    private static int playerTwoWins;
    private ArrayList<GameInput> gamesList;

    public Games(final ArrayList<Deck> playerOneDecks,
                      final ArrayList<Deck> playerTwoDecks,
                      final ArrayList<GameInput> gamesList) {
        this.p1Decks = playerOneDecks;
        this.p2Decks= playerTwoDecks;
        this.gamesList = gamesList;
        playerOneWins = 0;
        playerTwoWins = 0;
    }

}
