package fileio;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class HeroInput {
    private int mana;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public HeroInput(){}

    public String toString() {
        return "HeroInput{"
                +  "mana="
                + mana
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }

}
