package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.fields.corners.JailField;

public class CardMove extends Card {

    private boolean doTravel;

    public CardMove(GameSystem system, String cardText, int value, boolean doTravel) {
        super(system, cardText, value);
        this.doTravel = doTravel;
    }

    @Override
    public void onPull() {
        super.onPull();

        if (doTravel)
            travel();
        else
            move();
    }

    private void travel() {
        var position = system.currentPlayer.getFieldPos();
        position += value;

        if (position >= 24)
            position -= 24;

        system.currentPlayer.setFieldPos(position);
    }

    private void move() {
        var previousPos = system.currentPlayer.getFieldPos();
        system.currentPlayer.setFieldPos(value);
        if (system.currentPlayer.getFieldPos() == 6)
            system.currentPlayer.setInJail(JailField.JAIL_TIME);
        else if (previousPos < system.currentPlayer.getFieldPos()) {
            system.fields.get(0).onEnter();
        }

        system.fields.get(system.currentPlayer.getFieldPos()).onStay();
    }
}
