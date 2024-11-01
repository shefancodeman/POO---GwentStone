package game.components;

import fileio.CardInput;
import game.cards.Card;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Deck {
    // deckul este un array de carti ce trebuie
    // sa ramana sortat
    private final ArrayList<Card> cards;
    private final int deckIdx;
    private int owner;

    // creem deckul si il populam in ordine
    public Deck(List<CardInput> initialCards, int deckIdx, int owner) {
        this.cards = new ArrayList<>();
        for (CardInput card: initialCards) {
            this.cards.add(Card.createCard(card, owner));
        }
        this.deckIdx = deckIdx;
        this.owner = owner;
    }

    // scoatem cel mai de sus card
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        else {
            return cards.remove(0);
        }
    }
}