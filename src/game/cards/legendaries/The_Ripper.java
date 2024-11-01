package game.cards.legendaries;

import fileio.CardInput;
import game.cards.Card;

public class The_Ripper extends Card {
    public The_Ripper(CardInput card, int owner) {
        super(card, owner);
    }

    // implementam Weak Knees
    @Override
    public String ability(Card card) {
        if (card.getAttack() <= 2) {
            card.setAttack(0);
        } else {
            card.setAttack(card.getAttack() - 2);
        }
        return null;
    }
}
