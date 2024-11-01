package game.components;

import fileio.CardInput;
import game.cards.Card;
import game.heroes.Hero;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Random;

@Getter
@Setter
public class Player {
    private final Deck deck;
    private final Hand hand;
    private final Hero hero;
    private final int id;
    private int mana;
    private final int[] assignedRows;
    private boolean turn;

    public Player(Deck deck, CardInput hero, int id) {
        this.deck = deck;
        this.hero = Hero.createHero(hero);
        this.id = id;
        this.hand = new Hand();
        this.mana = 1;
        // player 1 are rows 2 si 3, iar 2 are 0 si 1
        if (id == 1) {
            assignedRows = new int[]{2 , 3};
        } else { // nu tratam cazul in care id-ul nu este valid
            assignedRows = new int[]{0, 1};
        }
        this.turn = false;
    }

    // tragem carte, daca avem de unde, si o punem in mana
    public void drawCard() {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            hand.getCards().add(drawnCard);
        }
    }

    // verificare daca row-ul ne apartine
    private boolean checkRow(int row) {
        for (int assignedRow : assignedRows) {
            if (assignedRow == row) {
                return true;
            }
        }
        return false;
    }

    // punem o carte din mana, daca id-ul si row-ul sunt valide
    public String placeCard(Board board, int cardIdx, int row) {
        if (cardIdx < 0 || cardIdx >= hand.getCardsInHand().size()) {
            return "Invalid card.";
        }
        Card cardToPlace = hand.getCardsInHand().get(cardIdx);

        // verificare daca avem mana
        if (cardToPlace.getMana() > mana) {
            return "Not enough mana to place card on table.";
        }

        // scadem mana daca totul este ok si adaugam cartea pe masa
        if (board.addCard(cardToPlace, row)) {
            mana -= cardToPlace.getMana();
            hand.removeCard(cardIdx);
            return "Ok";
        } else {
            return "Cannot place card on table since row is full.";
        }
    }

}
