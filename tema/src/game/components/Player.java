package game.components;

import fileio.HeroInput;
import game.cards.Card;
import game.heroes.Hero;

public class Player {
    private final Deck deck;
    private final Hand hand;
    private final Hero hero;
    private final int id;
    private int mana;
    private final int[] assignedRows;

    public Player(Deck deck, HeroInput hero, int id) {
        this.deck = deck;
        this.hero = Hero.createHero(hero);
        this.id = id;
        this.hand = new Hand();
        this.mana = 0;
        // player 1 are rows 2 si 3, iar 2 are 0 si 1
        if (id == 1) {
            assignedRows = new int[]{2, 3};
        } else { // nu tratam cazul in care id-ul nu este valid
            assignedRows = new int[]{0, 1};
        }
    }

    // tragem carte, daca avem de unde, si o punem in mana
    public void drawCard() {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            hand.drawCard(drawnCard);
        } else {
            System.out.println("Empty deck.");
        }
    }

    // mana creste cu unu in fiecare tura
    public void gainMana() {
        mana++;
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
    public boolean placeCard(Board board, int cardIdx, int row) {
        if (cardIdx < 0 || cardIdx >= hand.getCardsInHand().size()) {
            System.out.println("Invalid card.");
            return false;
        }
        Card cardToPlace = hand.getCardsInHand().get(cardIdx);

        // verificare daca avem mana
        if (cardToPlace.getMana() > mana) {
            System.out.println("Not enough mana.");
            return false;
        }

        // verificam daca row-ul ne apartine
        if (!checkRow(row)) {
            System.out.println("Cannot place card on opponent's row.");
            return false;
        }

        // scadem mana daca totul este ok si adaugam cartea pe masa
        if (board.addCard(cardToPlace, row)) {
            mana -= cardToPlace.getMana();
            hand.placeCard(cardIdx);
            return true;
        } else {
            System.out.println("Full row.");
            return false;
        }
    }
}
