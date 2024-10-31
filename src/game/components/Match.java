package game.components;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import game.commands.Debug;
import game.commands.Play;
import game.heroes.Hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Match {
    public int startingPlayer;
    public int seed;
    public Player player1;
    public Player player2;
    public Board board;
    public ArrayNode output;
    public int turnCounter;
    public Match (ArrayNode output, GameInput game, ArrayList<Deck> p1Decks, ArrayList<Deck> p2Decks) {
        startingPlayer = game.getStartGame().getStartingPlayer();
        this.player1 = new Player(p1Decks.get(game.getStartGame().getPlayerOneDeckIdx()),  // creem playerii cu deckul apropriat
                game.getStartGame().getPlayerOneHero(), 1); // cat si eroul si id-ul care tebuie
        this.player2 = new Player(p2Decks.get(game.getStartGame().getPlayerTwoDeckIdx()), game.getStartGame().getPlayerTwoHero(), 2);

        seed = game.getStartGame().getShuffleSeed();
        // verificam cine incepe si dau shuffle la deck in ordine
        if (startingPlayer == 1) {
            player1.setTurn(true);
        } else {
            player2.setTurn(true);
        }
        Random random1 = new Random(seed);
        Collections.shuffle(player1.getDeck().getCards(), random1);
        Random random2 = new Random(seed);
        Collections.shuffle(player2.getDeck().getCards(), random2);
        this.board = new Board();


        // dupa la inceputul rundei primesc fiecare ce trebuie
        player1.drawCard();
        player1.gainMana();
        player2.drawCard();
        player2.gainMana();

        this.output = output;
        this.turnCounter = 1;
    }

    public ObjectNode play(ActionsInput action) {
        ObjectNode result = null;
            switch (action.getCommand()) {
                    // comenzi pentru joc
                case "endPlayerTurn": Play.endTurn(this);
                    break;
                case "placeCard": result = Play.placeCard(this, action);
                    break;
                case "cardUsesAttack":
                    break;
                case "cardUsesAbility":
                    break;
                case "useAttackHero":
                    break;
                case "useHeroAbility":
                    break;

                    //comenzi pt debug
                case "getPlayerDeck": result = Debug.getPlayerDeck(this, action);
                break;
                case "getCardsInHand": result = Debug.getCardsInHand(this, action);
                break;
                case "getCardsOnTable":
                    break;
                case "getPlayerTurn": result = Debug.getPlayerTurn(this);
                    break;
                case "getPlayerHero": result = Debug.getPlayerHero(this, action);
                    break;
                case "getCardAtPosition":
                    break;
                case "getPlayerMana": result = Debug.getPlayerMana(this, action);
                    break;
                case "getFrozenCardsOnTable":
                    break;

                    //comenzi pt statistici
                case "getTotalGamesPlayed":
                    break;
                case "getPlayerOneWins":
                    break;
                case "getPlayerTwoWins":
                    break;
            }
            return result;
        }
    }
