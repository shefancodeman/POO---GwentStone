package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class TheRipper extends Card {
    public TheRipper(final CardInput card, final int owner) {
        super(card, owner);
    }

    // implementam Weak Knees
    @Override
    public String ability(final Card card) {
        if (card.getAttack() <= 2) {
            card.setAttack(0);
        } else {
            card.setAttack(card.getAttack() - 2);
        }
        return null;
    }
}
