package game.cards;

import fileio.CardInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
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

    @Override
    public String toString() {
        return "Card{" +
                "mana=" + mana +
                ", attackDamage=" + attack +
                ", health=" + health +
                ", description='" + description + '\'' +
                ", colors=" + colors +
                ", name='" + name + '\'' +
                '}';
    }
}
