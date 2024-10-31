package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;
import game.cards.LegendaryCard;

import java.util.ArrayList;

public class The_Ripper extends LegendaryCard {
    public The_Ripper(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam Weak Knees
    @Override
    public String ability(Card card) {
        if(!isFrozen()) {
            if (this.getOwner() == card.getOwner()) {
                 return "Attacked card does not belong to the enemy.";
            } else {
                if (card.getAttack() >= 2)
                    card.setAttack(card.getAttack() - 2);
                else {
                    card.setAttack(0);
                }
                return "ok";
            }
        } else {
            return "Attacker card is frozen.";
        }
    }
}
