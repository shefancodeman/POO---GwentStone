package game.heroes;

import fileio.HeroInput;
import game.heroes.named.Empress_Thorina;
import game.heroes.named.General_Kocioraw;
import game.heroes.named.King_Mudface;
import game.heroes.named.Lord_Royce;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Hero {
    private int mana;
    private int health;
    private String description;
    private List<String> colors;
    private String name;
    private boolean used; // verificam daca am folosit abilitatea

    // constructor pentru erou
    public Hero(HeroInput hero) {
        this.mana = hero.getMana();
        this.health = 30;
        this.description = hero.getDescription();
        this.colors = hero.getColors();
        this.name = hero.getName();
        this.used = false;
    }

    // Factory pattern pentru a creea eroul potrivit dupa nume
    public static Hero createHero(HeroInput hero) {
        return switch (hero.getName()) {
            case "Lord Royce" -> new Lord_Royce(hero);
            case "General Kocioraw" -> new General_Kocioraw(hero);
            case "King Mudface" -> new King_Mudface(hero);
            case "Empress Thorina" -> new Empress_Thorina(hero);
            default -> throw new IllegalArgumentException("Unknown hero name: " + hero.getName());
        };
    }
    public abstract void ability(int row);
}
