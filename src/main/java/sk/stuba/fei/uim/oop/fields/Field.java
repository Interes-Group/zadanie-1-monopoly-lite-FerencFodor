package sk.stuba.fei.uim.oop.fields;

import sk.stuba.fei.uim.oop.GameSystem;

public abstract class Field {

    public GameSystem system;

    public Field(GameSystem system) {
        this.system = system;
    }

    /**
     * Action on entering a field
     */
    public abstract void onEnter();

    /**
     * Action while staying on a field
     */
    public abstract void onStay();

}
