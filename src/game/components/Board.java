package game.components;

import game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Board {
    // matrice pe care putem pune carti
    private final ArrayList<ArrayList<Card>> boardRows;

    public Board() {
        boardRows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boardRows.add(new ArrayList<Card>());
        }
    }

    // adaugam un card pe masa, indiferent de player
    public boolean addCard(Card card, int row) {
        if (row < 0 || row > 3 || boardRows.get(row).size() >= 5) {
            return false;  // rowul nu exista / e full
        }
        boardRows.get(row).add(card);
        card.setX(row);
        card.setY(boardRows.get(row).indexOf(card));
        return true;
    }

    // cand o carte moare trebuie sa shiftam pozitia
    public void removeCard(int row, int col) {
        if (row >= 0 && row < 4 && col >= 0 && col < boardRows.get(row).size()) {
            Card card = boardRows.get(row).get(col);
            card.setX(-1);
            card.setY(-1);
            // scoatem cardul de pe masa
            boardRows.get(row).remove(col);
        }
    }

    // inspectam o carte de pe board
    public Card getCard(int row, int col) {
        if ((row >= 0 && row < 4) && (col >= 0 && col < boardRows.get(row).size())) {
                return boardRows.get(row).get(col);
            }
        return null;
    }

    // resetam atacurile/frozen status pentru unul dintre playeri
    // untap este termen de Magic the Gathering dar ¯\_(ツ)_/¯
    public void untap(int playerIdx) {
        for (int i = 0; i < 4; i++) {
            for (Card card: boardRows.get(i)) {
                if (card != null && card.getOwner() == playerIdx) {
                    card.setUsable(true);
                    card.setFrozen(false);
                }
            }
        }
    }

    // luam un row de carti
    public List<Card> getRow(int row) {
        return new ArrayList<>(boardRows.get(row));
    }

    @Override
    public String toString() {
        return boardRows.toString();
    }
}
