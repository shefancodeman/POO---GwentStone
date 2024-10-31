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
    private final ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }
    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }
    public void removeCard(int idx) {
        this.cards.remove(idx);
    }

    public List<Card> getCardsInHand() {
        return new ArrayList<>(cards);
    }

    @Override
    public String toString() {
        return "Hand{" + "cardsInHand=" + cards + '}';
    }
}
