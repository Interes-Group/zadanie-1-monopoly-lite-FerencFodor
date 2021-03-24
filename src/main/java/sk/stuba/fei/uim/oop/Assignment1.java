package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.exceptions.MissingArgumentException;

public class Assignment1 {
    public static void main(String[] args) {
        GameSystem game = new GameSystem();
        try {
            game.startGame();
        } catch (MissingArgumentException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
