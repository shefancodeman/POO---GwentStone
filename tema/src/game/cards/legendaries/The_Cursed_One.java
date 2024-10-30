package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;
import game.cards.LegendaryCard;

import java.util.ArrayList;

public class The_Cursed_One extends LegendaryCard {
    public The_Cursed_One(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam Shapeshift
    @Override
    public String ability(Card card) {
        if (!isFrozen()) {
            if (this.getOwner() == card.getOwner()) {
                return "Attacked card does not belong to the enemy.";
            } else {
                if (card.getAttack() == 0) {
                    //TODO
                } else {
                    int temp = card.getAttack();
                    card.setAttack(temp);
                    card.setHealth(temp);
                }
                return "ok";
            }
        } else {
            return "Attacker card is frozen.";
        }
    }
}
