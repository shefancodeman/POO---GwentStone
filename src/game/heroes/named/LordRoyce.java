package game.heroes.named;

import fileio.CardInput;
import game.cards.Card;
import game.components.Board;
import game.heroes.Hero;

public class LordRoyce extends Hero {
    public LordRoyce(CardInput hero) {
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
        if (playerIdx == 1) {
            if (row == 2 || row == 3) {
                return "Selected row does not belong to the enemy.";
            }
        } else {
            if (row == 0 || row == 1) {
                return "Selected row does not belong to the enemy.";
            }
        }

        // daca folosim abilitatea, nu o putem folosi din nou
        this.setUsable(false);
        // iteram prin fiecare carte din row si ii dam conditia Frozen
        for (Card card: board.getRow(row)) {
            card.setFrozen(true);
        }
        return null;
    }


}
