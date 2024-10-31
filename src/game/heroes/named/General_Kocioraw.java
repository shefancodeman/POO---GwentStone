package game.heroes.named;

import fileio.CardInput;
import game.heroes.Hero;

import java.util.List;

public class General_Kocioraw extends Hero {
    public General_Kocioraw(CardInput hero) {
        super(hero);
    }

    @Override
    public void ability(int row) {
        if (this.isUsed()) {

        } else {
            System.out.println("Hero has already attacked this turn.");
        }
    }
}
