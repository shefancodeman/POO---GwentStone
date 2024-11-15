package game.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import game.cards.legendaries.Disciple;
import game.cards.legendaries.Miraj;
import game.cards.legendaries.TheCursedOne;
import game.cards.legendaries.TheRipper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
// clasa pentru cartile obisnuite, va fi extinsa pentru legendare
public class Card {
    private int mana;
    private int attack;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int x, y;   // pozitia
    private final int owner; // playerul care detine cardul`
    private boolean frozen;
    private boolean usable; // verif daca deja a atacat

    // constructor
    public Card(final CardInput card, final int owner) {
        this.mana = card.getMana();
        this.attack = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.owner = owner;
        this.frozen = false; // cardul nu intra inghetat
        this.usable = true; // si poate ataca
        this.x = -1;
        this.y = -1; // cardul nu e pe masa cand e initializat
    }

    // functie pentru a transorma din Card in ObjectNode, era nevoie pentru
    // afisarea corecta in JSON
    public final ObjectNode cardObj() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardNode = objectMapper.createObjectNode();

        // adaugam caracteristicile cardului
        cardNode.put("mana", this.mana);
        cardNode.put("attackDamage", this.attack);
        cardNode.put("health", this.health);
        cardNode.put("description", this.description);

        // culorile sunt un arraylist pe un node
        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        cardNode.set("colors", colorsNode);
        cardNode.put("name", this.getName());

        // returnam cardul!
        return cardNode;
    }

    // Factory pattern pentru a crea cartile legendare dupa nume
    public static final Card createCard(final CardInput card, final int owner) {
        return switch (card.getName()) {
            case "The Ripper" -> new TheRipper(card, owner);
            case "Disciple" -> new Disciple(card, owner);
            case "The Cursed One" -> new TheCursedOne(card, owner);
            case "Miraj" -> new Miraj(card, owner);
            default -> new Card(card, owner);
        };
    }

    // cartile normale nu au abilitati, dar cele legendare da
    // acestea vor da ovveride la ability
    public String ability(final Card card) {
        return null;
    }
}
