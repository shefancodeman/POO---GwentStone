package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class Miraj extends Card {
    public Miraj(final CardInput card, final int owner) {
        super(card, owner);
    }

    // implementam skyjack
    @Override
    public String ability(final Card card) {
        int temp = this.getHealth();
        this.setHealth(card.getHealth());
        card.setHealth(temp);
        return null;
    }
}
