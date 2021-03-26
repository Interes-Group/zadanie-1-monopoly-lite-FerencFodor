package sk.stuba.fei.uim.oop.fields;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.Player;

import java.util.Arrays;
import java.util.Scanner;

public class HousingField extends Field {

    private static final String[] YES = {"yes", "y", "ano"};
    private static final String[] NO = {"no", "n", "nie"};

    private Player owner;
    private String name;
    private int value;
    private int rent;

    public HousingField(GameSystem system, String name, int value, int rent) {
        super(system);
        this.name = name;
        this.value = value;
        this.rent = rent;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }


    @Override
    public void onEnter() {

    }

    @Override
    public void onStay() {
        var money = system.currentPlayer.getMoney();

        if (owner != null) {
            if (owner == system.currentPlayer) {
                System.out.println(system.currentPlayer.getName() + " landed on their own property.");
                return;
            }

            System.out.println(system.currentPlayer.getName() + " pays " + owner.getName() + " $" + rent);

            system.currentPlayer.setMoney(money - rent);
            owner.setMoney(owner.getMoney() + rent);
        } else {
            var scanner = new Scanner(System.in);

            System.out.println("Would you like to buy " + name + " for $" + value + "?");
            System.out.println("Current balance: $" + system.currentPlayer.getMoney());

            if (system.currentPlayer.getMoney() < value) {
                System.out.println("You don't have enough money to buy this property.");
                return;
            }

            while (true) {
                var answer = scanner.next();

                if (Arrays.asList(YES).contains(answer.toLowerCase())) {
                    system.currentPlayer.setMoney(money - value);
                    owner = system.currentPlayer;
                    system.currentPlayer.addHousingField(this);
                    System.out.println(system.currentPlayer.getName() + " bought " + name);
                    break;
                } else if (Arrays.asList(NO).contains(answer.toLowerCase())) {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }
    }
}
