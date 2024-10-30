package game.heroes.named;

import fileio.HeroInput;
import game.heroes.Hero;

import java.util.List;

public class Lord_Royce extends Hero {
    public Lord_Royce(HeroInput hero) {
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
