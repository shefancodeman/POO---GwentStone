package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;
import game.cards.LegendaryCard;

import java.util.ArrayList;

public class Disciple extends LegendaryCard {
    public Disciple(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam God's Plan
    @Override
    public String ability(Card card) {
        if(!isFrozen()) {
            if (!(card.getOwner() == this.getOwner())) {
                return "Attacked card does not belong to the current player.";
            } else {
                card.setAttack(card.getHealth() + 2);
                return "ok";
            }
        } else {
            return "Attacker card is frozen.";
        }
    }
}
