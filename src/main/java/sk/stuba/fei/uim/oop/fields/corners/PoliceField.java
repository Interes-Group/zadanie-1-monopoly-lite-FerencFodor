package sk.stuba.fei.uim.oop.fields.corners;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.fields.Field;

public class PoliceField extends Field {

    public PoliceField(GameSystem system) {
        super(system);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onStay() {
        var player = system.currentPlayer;
        System.out.println(player.getName() + " is sent to Jail!");
        player.setFieldPos(6);
        player.setInJail(JailField.JAIL_TIME);
    }
}
