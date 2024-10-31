package game.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.cards.Card;
import game.components.Match;

public class Play {
    // void din moment ce nu trebuie sa outputam nimic
    public static void endTurn(Match match) {
        // daca ambii si-au terminat tura incepem runda noua
        if (match.startingPlayer == 1) {
            if (match.player1.isTurn()) {
                // trecem la tura jucatorului al doilea
                match.player1.setTurn(false);
                match.player2.setTurn(true);

                // resetam board-ul jucatorului 1
                match.board.untap(1);
            } else {
                // avem runda noua, din moment ce primul a inceput jocul
                match.player1.setTurn(true);
                match.player2.setTurn(false);

                match.turnCounter++;
                match.player1.setMana(match.player1.getMana() + match.turnCounter);
                match.player2.setMana(match.player2.getMana() + match.turnCounter);
                match.player1.drawCard();
                match.player2.drawCard();

                match.board.untap(2);
            }
        } else {
            if (match.player2.isTurn()) {
                // la fel, trecem la tura jucatorului care nu a inceput
                match.player1.setTurn(true);
                match.player2.setTurn(false);

                // resetam board-ul jucatorului 1
                match.board.untap(2);
            } else {
                // avem runda noua, din moment ce a doilea a inceput jocul
                match.player1.setTurn(false);
                match.player2.setTurn(true);

                match.turnCounter++;
                match.player1.setMana(match.player1.getMana() + match.turnCounter);
                match.player2.setMana(match.player2.getMana() + match.turnCounter);
                match.player1.drawCard();
                match.player2.drawCard();

                match.board.untap(1);
            }
        }

    }

    // functia de a plasa cartea pe masa, o avem deja in Board creata
    public static ObjectNode placeCard(Match match, ActionsInput action) {
        if (match.player1.isTurn()) {
            // luam cardul din mana playerului
            Card card = match.player1.getHand().getCards().get(action.getHandIdx());
            int row;
            // trebuie sa verificam, dupa numele cardului, unde il punem
            if (card.getName().equals("The Ripper")
                    || card.getName().equals("Miraj")
                    || card.getName().equals("Goliath")
                    || card.getName().equals("Warden")) {
                row = 2;
            } else {
                row = 3;
            }
            String message = match.player1.placeCard(match.board, action.getHandIdx(), row);
            if (message.equals("Ok")) {
                // yipee
            } else {
                ObjectNode print = null;
                ObjectMapper objectMapper = new ObjectMapper();
                print = objectMapper.createObjectNode();
                print.put("command", "placeCard");
                print.put("handIdx", action.getHandIdx());
                print.put("error", message);
                return print;
            }
        } else {
            // luam cardul din mana playerului
            if (match.player2.getHand().getCardsInHand().size() <= action.getHandIdx()) {
                return null;
            }
            Card card = match.player2.getHand().getCards().get(action.getHandIdx());
            int row;
            // trebuie sa verificam, dupa numele cardului, unde il punem
            if (card.getName().equals("The Ripper")
                    || card.getName().equals("Miraj")
                    || card.getName().equals("Goliath")
                    || card.getName().equals("Warden")) {
                row = 1;
            } else {
                row = 0;
            }
            String message = match.player2.placeCard(match.board, action.getHandIdx(), row);
            if (message.equals("Ok")) {
                // yipee
            } else {
                ObjectNode print = null;
                ObjectMapper objectMapper = new ObjectMapper();
                print = objectMapper.createObjectNode();
                print.put("command", "placeCard");
                print.put("handIdx", action.getHandIdx());
                print.put("error", message);
                return print;
            }
        }
        return null;
    }
}
