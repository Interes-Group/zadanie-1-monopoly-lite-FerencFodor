package sk.stuba.fei.uim.oop;


import sk.stuba.fei.uim.oop.fields.HousingField;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int money;
    private int fieldPos;

    private int inJail;

    private List<HousingField> ownedHousingFields;

    //<editor-fold desc="Constructors">
    public Player(String name) {
        this.name = name;
        this.money = GameSystem.STARTING_CAPITAL;

        this.inJail = 0;
        ownedHousingFields = new ArrayList<>();
    }

    public Player() {
        this.money = GameSystem.STARTING_CAPITAL;

        this.inJail = 0;
        ownedHousingFields = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getFieldPos() {
        return fieldPos;
    }

    public void setFieldPos(int fieldPos) {
        this.fieldPos = fieldPos;
    }

    public int getInJail() {
        return inJail;
    }

    public void setInJail(int inJail) {
        this.inJail = inJail;
    }

    public List<HousingField> getOwnedHousingFields() {
        return ownedHousingFields;
    }

    public void setOwnedHousingFields(List<HousingField> ownedHousingFields) {
        this.ownedHousingFields = ownedHousingFields;
    }

    public void addHousingField(HousingField housingField){
        this.ownedHousingFields.add(housingField);
    }

    //</editor-fold>

    public void status(){
        System.out.println(
                "[" + name +"]\n" +
                "Money: $" + money +"\n" +
                "Owned Property:");

        ownedHousingFields.forEach(x -> System.out.println(x.getName()));
    }

    public void help() {}

}
