package sk.stuba.fei.uim.oop.fields;

import sk.stuba.fei.uim.oop.GameSystem;

public class ChanceField extends Field {


    public ChanceField(GameSystem system) {
        super(system);
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onStay() {
        system.getCard();
    }
}
