package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.Player;

import java.util.List;

public abstract class Card {

    public String cardText;
    public GameSystem system;
    public int value;

    public Card(GameSystem system, String cardText, int value) {
        this.system = system;
        this.cardText = cardText;
        this.value = value;
    }

    /**
     * Retrieve a card while standing on a Chance Field
     *
     * @param playerList List of players affected
     */
    public abstract void onPull(List<Player> playerList);
}
