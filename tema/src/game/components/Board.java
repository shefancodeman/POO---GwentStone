package game.components;

import game.cards.Card;
import game.cards.Position;

import java.util.ArrayList;
import java.util.List;

public class Board {
    // matrice pe care putem pune carti
    private final ArrayList<ArrayList<Card>> boardRows;

    public Board() {
        boardRows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boardRows.add(new ArrayList<>());
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
            // shiftam la stanga
            for (int i = col; i < boardRows.get(row).size() - 1; i++) {
                boardRows.get(row).set(i, boardRows.get(row).get(i + 1));
            }
            // scoate duplicatul
            boardRows.get(row).remove(boardRows.get(row).size() - 1);
        }
    }

    // inspectam o carte de pe board
    public Card getCard(int row, int col) {
        if (row >= 0 && row < 4 && col >= 0 && col < boardRows.get(row).size()) {
            return boardRows.get(row).get(col);
        }
        return null;
    }

    // Method to get an entire row of cards
    public List<Card> getRow(int row) {
        return new ArrayList<>(boardRows.get(row));
    }

    @Override
    public String toString() {
        return boardRows.toString();
    }
}
