package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;
import game.cards.LegendaryCard;

import java.util.ArrayList;

public class Miraj extends LegendaryCard {
    public Miraj(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam skyjack
    @Override
    public String ability(Card card) {
        if(!isFrozen()) {
            if (this.getOwner() == card.getOwner()) {
                return "Attacked card does not belong to the enemy.";
            } else {
                int temp = this.getHealth();
                this.setHealth(card.getHealth());
                card.setHealth(temp);
                return "ok";
            }
        } else {
            return "Attacker card is frozen.";
        }
    }
}
