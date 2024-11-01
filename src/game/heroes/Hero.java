package game.heroes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import game.components.Board;
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
    private boolean usable; // verificam daca am folosit abilitatea

    // constructor pentru erou, cu health-ul 30
    // si poate folosi abilitatea (usable e true)
    public Hero(CardInput card) {
        this.mana = card.getMana();
        this.health = 30;
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.usable = true;
    }

    // Factory pattern pentru a creea eroul dupa nume
    public static Hero createHero(CardInput hero) {
        return switch (hero.getName()) {
            case "Lord Royce" -> new Lord_Royce(hero);
            case "General Kocioraw" -> new General_Kocioraw(hero);
            case "King Mudface" -> new King_Mudface(hero);
            case "Empress Thorina" -> new Empress_Thorina(hero);
            default -> throw new IllegalStateException("Unexpected value: " + hero.getName());
        };
    }

    // acelasi constructor de ObjectNode ca la Card
    public ObjectNode heroObj () {
        // copiat 1 la 1 de la cardNode
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode heroNode = objectMapper.createObjectNode();

        // adaugam caracteristicile lui
        heroNode.put("mana", this.getMana());
        heroNode.put("health", this.getHealth());
        heroNode.put("description", this.getDescription());

        // culorile sunt un arraylist pe un node
        ArrayNode colorsNode = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsNode.add(color);
        }
        heroNode.set("colors", colorsNode);
        heroNode.put("name", this.getName());
        heroNode.put("health", this.getHealth());

        // returnam erou ca ObjectNode!
        return heroNode;
    }

    //fiecare erou afecteaza board-ul (sau un row) si e controlat de un player
    public abstract String ability(Board board, int row, int playerIdx);
}
