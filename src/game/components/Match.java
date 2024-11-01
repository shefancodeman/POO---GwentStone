package game.components;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.GameInput;
import game.commands.AttackCommands;
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
    public int winner;
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

        // board nou
        this.board = new Board();

        // dupa la inceputul rundei primesc fiecare cate un card
        player1.drawCard();
        player2.drawCard();

        // numaram turele si setam winner la 0, din
        // moment ce meciul de abea a inceput
        this.output = output;
        this.turnCounter = 1;
        this.winner = 0;
    }

    public ObjectNode play(ActionsInput command) {
        ObjectNode result = null;
            switch (command.getCommand()) {
                    // comenzi pentru joc
                case "endPlayerTurn": Play.endTurn(this);
                    break;
                case "placeCard": result = Play.placeCard(this, command);
                    break;
                case "cardUsesAttack": result = AttackCommands.attackCard(this, command);
                    break;
                case "cardUsesAbility": result = AttackCommands.cardUsesAbility(this, command);
                    break;
                case "useAttackHero": result = AttackCommands.useAttackHero(this, command);
                    break;
                case "useHeroAbility":
                    break;

                    //comenzi pt debug
                case "getPlayerDeck": result = Debug.getPlayerDeck(this, command);
                break;
                case "getCardsInHand": result = Debug.getCardsInHand(this, command);
                break;
                case "getCardsOnTable": result = Debug.getCardsOnTable(this);
                    break;
                case "getPlayerTurn": result = Debug.getPlayerTurn(this);
                    break;
                case "getPlayerHero": result = Debug.getPlayerHero(this, command);
                    break;
                case "getCardAtPosition": result = Debug.getCardAtPosition(this, command);
                    break;
                case "getPlayerMana": result = Debug.getPlayerMana(this, command);
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
