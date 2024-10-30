package game.heroes.named;

import fileio.HeroInput;
import game.heroes.Hero;

import java.util.List;

public class King_Mudface extends Hero {
    public King_Mudface(HeroInput hero) {
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
