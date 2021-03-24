package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.CardMove;
import sk.stuba.fei.uim.oop.cards.CardPay;
import sk.stuba.fei.uim.oop.exceptions.MissingArgumentException;
import sk.stuba.fei.uim.oop.fields.ChanceField;
import sk.stuba.fei.uim.oop.fields.Field;
import sk.stuba.fei.uim.oop.fields.HousingField;
import sk.stuba.fei.uim.oop.fields.corners.JailField;
import sk.stuba.fei.uim.oop.fields.corners.PoliceField;
import sk.stuba.fei.uim.oop.fields.corners.StartField;
import sk.stuba.fei.uim.oop.fields.corners.TaxField;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameSystem {
    public final static int MAX_PLAYER = 4;
    public final static int STARTING_CAPITAL = 1500;

    public Player currentPlayer;
    public int rollResult;
    public LinkedList<Field> fields;
    public Scanner scanner;

    private String[] cardNameList;
    private ArrayList<Card> cards;
    private ArrayList<Player> players;
    private int nextCard = 0;

    //Constructor
    public GameSystem() {
        cardNameList = new IOManager().fileToStringArray("CardText");

        fields = new LinkedList<>();
        cards = new ArrayList<>();
        players = new ArrayList<>();

        scanner = new Scanner(System.in);

    }

    //Methods

    /**
     * Populates Field List
     */
    private void populateFields() {
        fields.addFirst(new StartField(this));
        fields.add(new HousingField(this, "Mediterranean Avenue", 60, 10));
        fields.add(new ChanceField(this));
        fields.add(new HousingField(this, "Baltic Avenue", 60, 12));
        fields.add(new HousingField(this, "Oriental Avenue", 100, 15));
        fields.add(new HousingField(this, "Connecticut Avenue", 120, 20));

        fields.add(new JailField(this));
        fields.add(new HousingField(this, "St. Charles Avenue", 140, 40));
        fields.add(new HousingField(this, "Virginia Avenue", 160, 42));
        fields.add(new HousingField(this, "Tennessee Avenue", 180, 45));
        fields.add(new ChanceField(this));
        fields.add(new HousingField(this, "New York Avenue", 200, 50));

        fields.add(new TaxField(this));
        fields.add(new HousingField(this, "Kentucky Avenue", 220, 70));
        fields.add(new ChanceField(this));
        fields.add(new HousingField(this, "Illinois Avenue", 240, 72));
        fields.add(new HousingField(this, "Ventnor Avenue", 260, 75));
        fields.add(new HousingField(this, "Marvin Gardens", 280, 80));

        fields.add(new PoliceField(this));
        fields.add(new HousingField(this, "Pacific Avenue", 300, 100));
        fields.add(new HousingField(this, "Pennsylvania Avenue", 320, 102));
        fields.add(new ChanceField(this));
        fields.add(new HousingField(this, "Park Palace", 350, 105));
        fields.addLast(new HousingField(this, "Boardwalk", 400, 110));
    }

    /**
     * Populates Card List
     */
    private void populateCards() {

        cards.add(new CardPay(this, cardNameList[1], 50, false));
        cards.add(new CardMove(this, cardNameList[2], 15, false));
        cards.add(new CardMove(this, cardNameList[3], 7, false));
        cards.add(new CardPay(this, cardNameList[4], 50, false));
        cards.add(new CardPay(this, cardNameList[5], 150, false));
        cards.add(new CardMove(this, cardNameList[0], 0, false));
        cards.add(new CardMove(this, cardNameList[6], -3, true));
        cards.add(new CardMove(this, cardNameList[7], 6, false));
        cards.add(new CardPay(this, cardNameList[8], -15, false));
        cards.add(new CardMove(this, cardNameList[9], 23, false));
        cards.add(new CardPay(this, cardNameList[10], 50, true));
    }

    /**
     * Generates a random value between 1 and 6
     *
     * @return generated integer value
     */
    public int roll() {
        return (int) Math.round((Math.random() % 6) + 1);
    }

    /**
     * Gets the next card from the card list
     */
    public void getCard() {
        nextCard = nextCard >= cards.size() ? 0 : nextCard;

        cards.get(nextCard).onPull(players);

        nextCard++;
    }

    /**
     * Waits for the enter key to be pressed
     */
    private void pressEnterToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            var ignore = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the field the player landed on
     */
    private void checkLandedField(Field field) {
        if (field instanceof ChanceField)
            System.out.println("a Chance Field!");
        else if (field instanceof HousingField)
            System.out.println(((HousingField) field).getName() + "!");
        else if (field instanceof StartField)
            System.out.println("GO Field!");
        else if (field instanceof JailField)
            System.out.println("Jail Field!");
        else if (field instanceof TaxField)
            System.out.println("Tax Field!");
        else if (field instanceof PoliceField)
            System.out.println("Police Field!");
    }

    /**
     * Prints help for start commands
     */
    private void printHelp() {
        System.out.print("help - Usage: help\n\tprint help info.\n" +
                "add - Usage: add [playerName]\n\tAdds a player to game\n" +
                "remove - Usage: remove [playerName]\n\tRemoves player from game\n" +
                "start - Usage: start\n\tStarts the game");
    }


    //Game loop methods

    /**
     * Entry point for the game loop
     */
    public void startGame() throws MissingArgumentException, InterruptedException {
        var addedPlayers = 0;
        var start = false;

        System.out.print(
                "Monopoly Lite\n" +
                        "Author: Ferenc Fodor\n\n" +

                        "Max players: " + MAX_PLAYER + "\n" +
                        "(type \"help\" for commands)\n"
        );


        while (addedPlayers < MAX_PLAYER) {
            var input = scanner.next();
            String arg = "";
            if (!input.equalsIgnoreCase("start"))
                arg = scanner.next();

            switch (input) {
                case "add":
                    if (arg == null)
                        throw new MissingArgumentException("Not enough arguments");

                    players.add(new Player(arg));
                    System.out.println("Player Added: " + arg);
                    addedPlayers++;
                    break;

                case "remove":

                    if (arg == null)
                        throw new MissingArgumentException("Not enough arguments");

                    String finalArg = arg;
                    players.removeIf(player -> player.getName().equals(finalArg));
                    System.out.println("Player Removed: " + arg);
                    addedPlayers--;
                    break;

                case "start":
                    start = true;
                    break;

                case "help":
                    printHelp();
                    break;
            }

            if (start) break;
        }

        System.out.println("\nStarting game...");

        populateFields();
        populateCards();

        update();
    }

    /**
     * Main Game Loop
     */
    private void update() throws InterruptedException {
        Iterator<Player> it = players.iterator();

        System.out.println("In Game Commands: help, status, roll");
        do {
            TimeUnit.SECONDS.sleep(1);

            System.out.println("----------");

            //Cycle through players using iterator
            if (!it.hasNext())
                it = players.iterator();
            currentPlayer = it.next();

            //Check if player is in jail
            //Used continue because player can't loose money while in jail
            if (currentPlayer.getInJail() > 0) {
                fields.get(currentPlayer.getFieldPos()).onStay();
                continue;
            }

            System.out.println(currentPlayer.getName() + "'s Turn:\n");

            String input;
           do {
                input = scanner.next();
                switch (input) {
                    case "help":
                        currentPlayer.help();
                        break;
                    case "status":
                        currentPlayer.status();
                        break;
                }
            } while(!input.equals("roll"));


            System.out.println("Rolling...");
            TimeUnit.SECONDS.sleep(1);

            rollResult = roll();
            System.out.println(currentPlayer.getName() + " rolled " + rollResult + ".");

            for (var i = 0; i < rollResult; i++) {
                var pos = currentPlayer.getFieldPos() + 1;

                if (pos > 23)
                    pos = 0;

                currentPlayer.setFieldPos(pos);
                fields.get(pos).onEnter();
            }

            showPosition();

            System.out.print(currentPlayer.getName() + " landed on ");
            checkLandedField(fields.get(currentPlayer.getFieldPos()));

            fields.get(currentPlayer.getFieldPos()).onStay();

            //remove player and ownership of bought housing fields if their money is below or equal to 0
            players.forEach(player -> {
                if (player.getMoney() <= 0) {
                    players.remove(player);
                    fields.forEach(field -> {
                        if (field instanceof HousingField &&
                                ((HousingField) field).getOwner().equals(player))
                            ((HousingField) field).setOwner(null);
                    });
                }
            });
        } while (players.size() != 1);


        System.out.print("WINNER: " + players.get(0).getName());
        scanner.close();
    }

    private void showPosition() {
        PrintStream stream = System.out;
        char[] playerPos = new char[48];

        fields.forEach(field -> {
            if(field instanceof StartField)
                stream.print("|G");
            else if(field instanceof HousingField)
                stream.print(" ^" + ((HousingField) field).getName().charAt(0));
            else if(field instanceof ChanceField)
                stream.print(" |C");
            else if(field instanceof JailField)
                stream.print(" |J");
            else if (field instanceof TaxField)
                stream.print(" |T");
            else if(field instanceof PoliceField)
                stream.print(" |P");
        });
        stream.print("\n");

        players.forEach(player -> playerPos[1 + player.getFieldPos() * 3] = player.getName().charAt(0));
        stream.println(playerPos);
    }
}
