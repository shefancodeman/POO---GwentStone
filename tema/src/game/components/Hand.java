package game.components;

import game.cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Hand {
    private final List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public void drawCard(Card card) {
        cards.add(card);
    }

    public Card placeCard(int handIdx) {
        if (handIdx >= 0 && handIdx < cards.size()) {
            return cards.remove(handIdx);
        }
        return null;
    }

    public List<Card> getCardsInHand() {
        return new ArrayList<>(cards);
    }

    @Override
    public String toString() {
        return "Hand{" + "cardsInHand=" + cards + '}';
    }
}
