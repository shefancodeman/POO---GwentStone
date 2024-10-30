package game.components;

import fileio.CardInput;
import game.cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Deck {
    private final List<Card> cards;
    private final int deckIdx;
    private int owner;

    public Deck(List<CardInput> initialCards, int deckIdx, int owner) {
        this.cards = new LinkedList<>();
        for (CardInput card: initialCards) {
            this.cards.add(new Card(card, owner));
        }
        this.deckIdx = deckIdx;
        this.owner = owner;
    }

    public void shuffle(int seed) {
        Collections.shuffle(cards, new Random(seed));
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

    public ArrayList<Card> getCards() {
        return new ArrayList<>(cards);
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards + ", deckIdx=" + deckIdx + '}';
    }
}