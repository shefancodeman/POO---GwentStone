package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class TheCursedOne extends Card {
    public TheCursedOne(final CardInput card, final int owner) {
        super(card, owner);
    }

    // implementam Shapeshift
    @Override
    public String ability(final Card card) {
        int temp = card.getAttack();
        card.setAttack(card.getHealth());
        card.setHealth(temp);
        return null;
    }
}
