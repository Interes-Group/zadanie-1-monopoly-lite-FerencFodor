package sk.stuba.fei.uim.oop.fields.corners;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.fields.Field;

public class JailField extends Field {

    public static final int JAIL_TIME = 3;

    public JailField(GameSystem system) {
        super(system);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onStay() {
        if (system.currentPlayer.getInJail() > 0) {
            System.out.println(system.currentPlayer.getName() + " is in Jail for "
                    + system.currentPlayer.getInJail() + " rounds!");
            system.currentPlayer.setInJail(system.currentPlayer.getInJail() - 1);
        }
    }
}
