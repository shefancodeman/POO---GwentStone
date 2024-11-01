package game.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.cards.Card;
import game.components.Match;

public class Play {
    // void din moment ce nu trebuie sa outputam nimic
    public static void endTurn(Match match) {
        // daca meciul este gata, nu mai avem ture
        if (match.winner == 0) {
            // daca jucatorul care a inceput jocul si-a terminat tura, trecem
            // la jucatorul care nu a jucat inca runda asta
            if (match.startingPlayer == 1) {
                if (match.player1.isTurn()) {
                    // trecem la tura jucatorului al doilea
                    match.player1.setTurn(false);
                    match.player2.setTurn(true);
                    match.player2.getHero().setUsable(true);

                    // resetam board-ul jucatorului 1
                    match.board.untap(1);
                } else {
                    // daca jucatorul al doilea si-a terminat tura, avem o runda noua
                    match.player1.setTurn(true);
                    match.player2.setTurn(false);
                    match.player1.getHero().setUsable(true);

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
                    match.player2.getHero().setUsable(true);

                    // resetam board-ul jucatorului 1
                    match.board.untap(2);
                } else {
                    // avem runda noua, din moment ce a doilea a inceput jocul
                    match.player1.setTurn(false);
                    match.player2.setTurn(true);
                    match.player1.getHero().setUsable(true);

                    match.turnCounter++;
                    match.player1.setMana(match.player1.getMana() + match.turnCounter);
                    match.player2.setMana(match.player2.getMana() + match.turnCounter);
                    match.player1.drawCard();
                    match.player2.drawCard();

                    match.board.untap(1);
                }
            }

        }
    }

    // functia de a plasa cartea pe masa, o avem deja in Board creata
    public static ObjectNode placeCard(Match match, ActionsInput command) {
        if (match.player1.isTurn()) {
            if (match.player1.getHand().getCardsInHand().size() <= command.getHandIdx()) {
                return null;
            }
            // luam cardul din mana playerului
            Card card = match.player1.getHand().getCards().get(command.getHandIdx());
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
            String message = match.player1.placeCard(match.board, command.getHandIdx(), row);
            if (message.equals("Ok")) {
                // yipee
            } else {
                ObjectNode print = null;
                ObjectMapper objectMapper = new ObjectMapper();
                print = objectMapper.createObjectNode();
                print.put("command", "placeCard");
                print.put("handIdx", command.getHandIdx());
                print.put("error", message);
                return print;
            }
        } else {
            // luam cardul din mana playerului
            if (match.player2.getHand().getCardsInHand().size() <= command.getHandIdx()) {
                return null;
            }
            Card card = match.player2.getHand().getCards().get(command.getHandIdx());
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
            String message = match.player2.placeCard(match.board, command.getHandIdx(), row);
            if (message.equals("Ok")) {
                // yipee
            } else {
                ObjectNode print = null;
                ObjectMapper objectMapper = new ObjectMapper();
                print = objectMapper.createObjectNode();
                print.put("command", "placeCard");
                print.put("handIdx", command.getHandIdx());
                print.put("error", message);
                return print;
            }
        }
        return null;
    }

    public static ObjectNode useHeroAbility(Match match, ActionsInput command) {
        // daca match-ul este gata, nu putem folosi abilitatea
        if (match.winner != 0) {
            return null;
        }
        String message = null;

        // folosim getPlayerTurn pentru a afla ce erou folosim
        int playerIdx = Debug.getPlayerTurn(match).get("output").asInt();

        if (playerIdx == 1) {
            // singurul caz de care nu are grija Hero.ability() este cel in care
            // playerul nu are suficienta mana
            if (match.player1.getMana() < match.player1.getHero().getMana()) {
                message = "Not enough mana to use hero's ability.";
            } else {
                message = match.player1.getHero().ability(match.board, command.getAffectedRow(), 1);
            }
        } else {
            if (match.player2.getMana() < match.player2.getHero().getMana()) {
                message = "Not enough mana to use hero's ability.";
            } else {
                message = match.player2.getHero().ability(match.board, command.getAffectedRow(), 2);
            }
        }

        // daca message ramane null, inseamna ca Hero.ability() s-a executat cu succes
        if(message == null) {
            //nu uitam sa scadem din mana jucatorului costul abilitatii
            if (playerIdx == 1) {
                match.player1.setMana(match.player1.getMana() - match.player1.getHero().getMana());
            } else {
                match.player2.setMana(match.player2.getMana() - match.player2.getHero().getMana());
            }
            return  null;
        }

        // printam mesajul obtinut cu ObjectNode
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        print.put("command", "useHeroAbility");
        print.put("affectedRow", command.getAffectedRow());
        print.put("error", message);
        return print;
    }
}
