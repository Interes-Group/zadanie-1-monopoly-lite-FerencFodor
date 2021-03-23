package sk.stuba.fei.uim.oop.fields.corners;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.fields.Field;

public class TaxField extends Field {


    public TaxField(GameSystem system) {
        super(system);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onStay() {
        var player = system.currentPlayer;
        var money = player.getMoney();

        System.out.println(player.getName() + " pays $200 in tax.");

        player.setMoney(money + 200);
    }
}
