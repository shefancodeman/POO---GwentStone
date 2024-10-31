package game.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.cards.Card;
import game.components.Match;

public class Debug {
    public static ObjectNode getCardsInHand (Match match, ActionsInput action) {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        // adaugam comanda si handIdx
        print.put("command", "getCardsInHand");

        // adaptam cardurile in format obj, din moment ce nu se printeaza corect
        ArrayNode temp = objectMapper.createArrayNode();
        if (action.getHandIdx() == 1) {
            for (Card card: match.player1.getHand().getCardsInHand()) {
                temp.add(card.cardObj());
            }
        } else {
            for (Card card: match.player2.getHand().getCardsInHand()) {
                temp.add(card.cardObj());
            }
        }
        // adaugam cartile la printare
        print.put("output", temp);
        print.put("playerIdx", action.getPlayerIdx());
        return print;
    }

    public static ObjectNode getPlayerDeck (Match match, ActionsInput action) {
        // identic ca la pasul anterior
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        // adaugam comanda si handIdx
        print.put("command", "getPlayerDeck");
        print.put("playerIdx", action.getPlayerIdx());

        // adaptam cardurile in format obj, din moment ce nu se printeaza corect
        ArrayNode temp = objectMapper.createArrayNode();
        if (action.getPlayerIdx() == 1) {
            for (Card card: match.player1.getDeck().getCards()) {
                temp.add(card.cardObj());
            }
        } else {
            for (Card card: match.player2.getDeck().getCards()) {
                temp.add(card.cardObj());
            }
        }
        // adaugam cartile la printare
        print.put("output", temp);
        return print;
    }

    public static ObjectNode getPlayerHero(Match match, ActionsInput action) {
        // identic ca la pasul anterior
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        // adaugam comanda si handIdx
        print.put("command", "getPlayerHero");
        print.put("playerIdx", action.getPlayerIdx());

        // adaptam eroul la format obj
        if (action.getPlayerIdx() == 1) {
            print.put("output", match.player1.getHero().heroObj());
        } else {
            print.put("output", match.player2.getHero().heroObj());
        }

        return print;
    }

    public static ObjectNode getPlayerTurn(Match match) {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        // adaugam comanda si atat, in rest nu cred ca trebuie sa explic
        print.put("command", "getPlayerTurn");

        if (match.player1.isTurn()) {
            print.put("output", 1);
        } else {
            print.put("output", 2);
        }
        return print;
    }

    public static ObjectNode getPlayerMana(Match match, ActionsInput actionsInput) {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        // adaugam comanda si atat, in rest nu cred ca trebuie sa explic
        print.put("command", "getPlayerMana");

        if (actionsInput.getPlayerIdx() == 1) {
            print.put("output", match.player1.getMana());
        } else {
            print.put("output", match.player2.getMana());
        }

        print.put("playerIdx", actionsInput.getPlayerIdx());
        return print;
    }

}
