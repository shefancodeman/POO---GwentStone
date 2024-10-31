package game.cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class LegendaryCard extends Card {
    public LegendaryCard(CardInput card, int owner) {
        super(card, owner);
    }
    // copiem constructorul de la Card


    // fiecare din cele 4 carti vor avea o abilitate diferita
    public abstract String ability(Card card);
}
