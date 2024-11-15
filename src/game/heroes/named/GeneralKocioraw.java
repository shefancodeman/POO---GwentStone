package game.heroes.named;

import fileio.CardInput;
import game.cards.Card;
import game.components.Board;
import game.heroes.Hero;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(CardInput hero) {
        super(hero);
    }

    @Override
    public String ability(Board board, int row, int playerIdx) {
        // verificam daca putem folosi abilitatea
        if (!this.isUsable()) {
            return "Hero has already attacked this turn.";
        }

        // verificam daca avem row-urile corecte - le vrem pe
        // ale celuilalt jucator
        if (playerIdx == 2) {
            if (row == 2 || row == 3) {
                return "Selected row does not belong to the current player.";
            }
        } else {
            if (row == 0 || row == 1) {
                return "Selected row does not belong to the current player.";
            }
        }

        // daca folosim abilitatea, nu o putem folosi din nou
        this.setUsable(false);
        // iteram prin fiecare carte din row si ii dam +1 atac
        for (Card card: board.getRow(row)) {
            if (card != null) {
                card.setAttack(card.getAttack() + 1);
            }
        }
        return null;
    }


}
