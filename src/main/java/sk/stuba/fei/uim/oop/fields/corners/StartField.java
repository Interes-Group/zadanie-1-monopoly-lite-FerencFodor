package sk.stuba.fei.uim.oop.fields.corners;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.fields.Field;

public class StartField extends Field {
    public StartField(GameSystem system) {
        super(system);
    }

    @Override
    public void onEnter() {
        PayPlayer(system, 200);
    }

    @Override
    public void onStay() {
        PayPlayer(system, 200);
    }

    private void PayPlayer(GameSystem system, int value) {
        var player = system.currentPlayer;
        var money = player.getMoney();

        System.out.println(player.getName() + " gets $" + value);

        player.setMoney(money + value);
    }
}
