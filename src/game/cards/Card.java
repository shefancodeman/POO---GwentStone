package game.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class Card {
    private int mana;
    private int attack;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int x, y;   // pozitia
    private int owner; // playerul care detine cardul`
    private boolean frozen;
    private boolean usable; // verif daca deja a atacat

    // constructor
    public Card(CardInput card, int owner) {
        this.mana = card.getMana();
        this.attack = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.owner = owner;
        this.frozen = false;
        this.usable = true;
        this.x = -1;
        this.y = -1; // cardul nu e pe masa cand e initializat
    }

    public ObjectNode cardObj () {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardNode = objectMapper.createObjectNode();

        // adaugam caracteristicile lui
        cardNode.put("mana", this.getMana());
        cardNode.put("attackDamage", this.getAttack());
        cardNode.put("health", this.getHealth());
        cardNode.put("description", this.getDescription());

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

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attack
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
