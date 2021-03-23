package sk.stuba.fei.uim.oop.fields;

import sk.stuba.fei.uim.oop.GameSystem;

public abstract class Field {

    public GameSystem system;

    public Field(GameSystem system) {
        this.system = system;
    }

    public abstract void onEnter();

    public abstract void onStay();

}
