package game.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.cards.Card;
import game.components.Match;

// am creat clasa pentru toate comenzile de tip attack
// attackCard, useAttackHero, useCardAbility din moment
// ce sunt toate trei foarte similare
public class AttackCommands {
    // creem o clasa ajutatoare pentru a transforma coordonate
    // in ObjectNode
    public static ObjectNode convert(int x, int y) {
        ObjectNode print = null;
        ObjectMapper objectMapper = new ObjectMapper();
        print = objectMapper.createObjectNode();
        print.put("x", x);
        print.put("y", y);
        return print;
    }

    // creem clasa pentru atacat carti
    public static ObjectNode attackCard(Match match, ActionsInput command) {
        String message = null;
        // cautam cardul atacat si cel ce ataca pe board
        // desfacem coordonatele in x si y
        int x = command.getCardAttacker().getX();
        int y = command.getCardAttacker().getY();
        Card cardAttacker = match.board.getCard(x, y);


        int z = command.getCardAttacked().getX();
        int v = command.getCardAttacked().getY();
        Card cardAttacked = match.board.getCard(z, v);
        if (cardAttacked == null || cardAttacker == null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "cardUsesAttack");
            print.put("cardAttacker", convert(x, y));
            print.put("cardAttacked", convert(z, v));
            print.put("error", "No card available at that position.");
            return print;
        }
        // verificam conditiile pe rand
        // daca au acelasi owner
        if (cardAttacker.getOwner() == cardAttacked.getOwner()) {
            message = "Attacked card does not belong to the enemy.";
            // daca deja au atacat
        } else if (!cardAttacker.isUsable()) {
            message = "Attacker card has already attacked this turn.";
            // daca sunt frozen
        } else if (cardAttacker.isFrozen()) {
            message = "Attacker card is frozen.";
            // verificam daca pe randurile unde pot fi Taunt Cards
            // sunt cu adevarat Taunt cards - randul 1 pt P2 si 2 pt P1
            // sarim acest pas daca atacam un card care are Taunt
        } else if (!cardAttacked.getName().equals("Goliath") && !cardAttacked.getName().equals("Warden")) {
            if (cardAttacker.getOwner() == 1) {
                for (Card card : match.board.getRow(1)) {
                    if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                        message = "Attacked card is not of type 'Tank'.";
                }
            } else if (cardAttacker.getOwner() == 2) {
                for (Card card : match.board.getRow(2)) {
                    if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                        message = "Attacked card is not of type 'Tank'.";
                }
            }
        }
        // verificam daca avem vreo eroare
        if (message != null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "cardUsesAttack");
            print.put("cardAttacker", convert(x, y));
            print.put("cardAttacked", convert(z, v));
            print.put("error", message);
            return print;
        }


        // daca nu are destul hp, o scoate de pe Board
        if (cardAttacked.getHealth() <= cardAttacker.getAttack()) {
            match.board.removeCard(z, v);
        } else {
            // daca are, ramane ranita </3
            cardAttacked.setHealth(cardAttacked.getHealth() - cardAttacker.getAttack());
        }
        cardAttacker.setUsable(false);
        return null;
    }

    public static ObjectNode cardUsesAbility(Match match, ActionsInput command) {
        String message = null;
        // functiile sunt foarte similare, de aceea antetul este
        // identic, modificand doar actiunea de la final
        int x = command.getCardAttacker().getX();
        int y = command.getCardAttacker().getY();
        Card cardAttacker = match.board.getCard(x, y);


        int z = command.getCardAttacked().getX();
        int v = command.getCardAttacked().getY();
        Card cardAttacked = match.board.getCard(z, v);

        if (cardAttacked == null || cardAttacker == null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "cardUsesAbility");
            print.put("cardAttacker", convert(x, y));
            print.put("cardAttacked", convert(z, v));
            print.put("error", "No card available at that position.");
            return print;
        }
        // Disciple functioneaza diferit, asa ca ii facem caz special
        if (cardAttacker.getName().equals("Disciple")) {
            message = cardAttacker.ability(cardAttacked);
            // verificam conditiile pe rand
            // daca au acelasi owner
        } else if (cardAttacker.isFrozen()) {
            message = "Attacker card is frozen.";
            // daca deja au atacat
        } else if (!cardAttacker.isUsable()) {
            message = "Attacker card has already attacked this turn.";
            // daca sunt frozen
        } else if (cardAttacker.getOwner() == cardAttacked.getOwner()) {
            message = "Attacked card does not belong to the enemy.";
            // verificam daca pe randurile unde pot fi Taunt Cards
            // sunt cu adevarat Taunt cards - randul 1 pt P2 si 2 pt P1
            // sarim acest pas daca atacam un card care are Taunt
        } else if (!cardAttacked.getName().equals("Goliath") && !cardAttacked.getName().equals("Warden")) {
            if (cardAttacker.getOwner() == 1) {
                for (Card card : match.board.getRow(1)) {
                    if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                        message = "Attacked card is not of type 'Tank'.";
                }
            } else if (cardAttacker.getOwner() == 2) {
                for (Card card : match.board.getRow(2)) {
                    if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                        message = "Attacked card is not of type 'Tank'.";
                }
            }
        }


        // verificam daca avem vreo eroare / daca avem Disciple
        if (message != null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "cardUsesAbility");
            print.put("cardAttacker", convert(x, y));
            print.put("cardAttacked", convert(z, v));
            print.put("error", message);
            return print;
        }


        // daca nu avem nici o eroare, vom declansa abilitatea
        cardAttacker.ability(cardAttacked);
        // abilitatiile lui Ripper si Cursed One pot omora, deci
        // trebuie sa verificam board-ul pentru carti cu atac negativ
        // cu health egal cu 0
        for (int i = 0; i < 4; i++) {
            for (Card card : match.board.getRow(i)) {
                if (card.getHealth() <= 0) {
                    match.board.removeCard(x, y);
                }
            }
        }
        // nu mai putem folosi cartea / ataca dupa abilitate
        cardAttacker.setUsable(false);
        return null;
    }

    public static ObjectNode useAttackHero(Match match, ActionsInput command) {
        // daca jocul e deja gata, nu mai putem ataca eroii
        if (match.winner != 0) {
            return null;
        }

        String message = null;

        // avem din nou attacker, dar fara defender de data asta
        int x = command.getCardAttacker().getX();
        int y = command.getCardAttacker().getY();
        Card cardAttacker = match.board.getCard(x, y);

        // verificam daca e null prima data
        if (cardAttacker == null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "useAttackHero");
            print.put("cardAttacker", convert(x, y));
            print.put("error", "No card available at that position.");
            return print;
        }

        // pe rand verificam conditiile - daca deja am atacat
        if (!cardAttacker.isUsable()) {
            message = "Attacker card has already attacked this turn.";
            // daca sunt frozen
        } else if (cardAttacker.isFrozen()) {
            message = "Attacker card is frozen.";
            // verificam daca pe randurile unde pot fi Taunt Cards
            // sunt cu adevarat Taunt cards - randul 1 pt P2 si 2 pt P1
            // sarim acest pas daca atacam un card care are Taunt
        } else if (cardAttacker.getOwner() == 1) {
            for (Card card : match.board.getRow(1)) {
                if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                    message = "Attacked card is not of type 'Tank'.";
            }
        } else if (cardAttacker.getOwner() == 2) {
            for (Card card : match.board.getRow(2)) {
                if (card.getName().equals("Goliath") || card.getName().equals("Warden"))
                    message = "Attacked card is not of type 'Tank'.";
            }
        }

        //fix ca in cardAttack
        if (message != null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("command", "useAttackHero");
            print.put("cardAttacker", convert(x, y));
            print.put("error", message);
            return print;
        }

        // verificam pentru fiecare erou daca am castigat sau nu
        if (cardAttacker.getOwner() == 1) {
            if (match.player2.getHero().getHealth() > cardAttacker.getAttack()) {
                // daca nu am castigat scadem hp-ul si mergem mai departe
                match.player2.getHero().setHealth(match.player2.getHero().getHealth()
                        - cardAttacker.getAttack());
            } else {
                // player 1 castiga, si setam winner-ul apropriat
                match.winner = 1;
                message = "Player one killed the enemy hero.";
                match.player2.getHero().setHealth(0);
            }
        } else if (cardAttacker.getOwner() == 2) {
            if (match.player1.getHero().getHealth() > cardAttacker.getAttack()) {
                // daca nu am castigat scadem hp-ul si mergem mai departe
                match.player1.getHero().setHealth(match.player1.getHero().getHealth()
                        - cardAttacker.getAttack());
            } else {
                // player 2 castiga
                match.winner = 2;
                message = "Player two killed the enemy hero.";
            }
        }

        // chiar daca am castigat sau nu, cartea nu poate ataca din nou
        cardAttacker.setUsable(false);

        // daca message nu este null, atunci unul din playeri a castigat!
        if (message != null) {
            ObjectNode print = null;
            ObjectMapper objectMapper = new ObjectMapper();
            print = objectMapper.createObjectNode();
            print.put("gameEnded", message);
            return print;
        }

        return null;
    }

}
