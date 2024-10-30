package game.heroes.named;

import fileio.HeroInput;
import game.heroes.Hero;

import java.util.List;

public class Empress_Thorina extends Hero {
    public Empress_Thorina(HeroInput hero) {
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
