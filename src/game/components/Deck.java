package game.components;

import fileio.CardInput;
import game.cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Deck {
    @Getter
    private final ArrayList<Card> cards;
    private final int deckIdx;
    private int owner;

    public Deck(List<CardInput> initialCards, int deckIdx, int owner) {
        this.cards = new ArrayList<>();
        for (CardInput card: initialCards) {
            this.cards.add(new Card(card, owner));
        }
        this.deckIdx = deckIdx;
        this.owner = owner;
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        else {
            return cards.remove(0);
        }
    }

    public int getDeckSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards + ", deckIdx=" + deckIdx + '}';
    }
}