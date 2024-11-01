package game.components;

import game.cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Hand {
    // hand-ul e o lista de carti la care adaugam
    private final ArrayList<Card> cards;

    // constructoare
    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void removeCard(int idx) {
        this.cards.remove(idx);
    }

    // getter
    public List<Card> getCardsInHand() {
        return new ArrayList<>(cards);
    }
}
