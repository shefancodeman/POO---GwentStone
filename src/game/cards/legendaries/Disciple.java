package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class Disciple extends Card {
    public Disciple(final CardInput card, final int owner) {
        super(card, owner);
    }

    // implementam God's Plan
    @Override
    public String ability(final Card card) {
        // din moment ce Disciple functioneaza pe cardurile proprii
        // am decis sa codez aici toate posibilitatile in ordine
        if (!this.isFrozen()) {
            if (this.isUsable()) {
                if (!(card.getOwner() == this.getOwner())) {
                    return "Attacked card does not belong to the current player.";
                } else {
                    card.setHealth(card.getHealth() + 2);
                    this.setUsable(false);
                    return null;
                }
            } else {
                return "Attacker card has already attacked this turn.";
            }
        } else {
            return "Attacker card is frozen.";
        }
    }
}
