package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class The_Cursed_One extends Card {
    public The_Cursed_One(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam Shapeshift
    @Override
    public String ability(Card card) {
        int temp = card.getAttack();
        card.setAttack(card.getHealth());
        card.setHealth(temp);
        return null;
    }
}
