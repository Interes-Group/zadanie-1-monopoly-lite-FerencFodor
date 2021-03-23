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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class GameSystem {
    public final static int MAX_PLAYER = 4;
    public final static int STARTING_CAPITAL = 1500;
    public Player currentPlayer;
    public int rolledValue;
    public LinkedList<Field> fields;
    public Scanner scanner;
    private String[] cardNameList;
    private ArrayList<Card> cards;
    private ArrayList<Player> players;
    private int nextCard = 0;

    public GameSystem() {
        cardNameList = new IOManager().fileToStringArray("CardText");

        fields = new LinkedList<>();
        cards = new ArrayList<>();
        players = new ArrayList<>();

        scanner = new Scanner(System.in);

    }

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

    private void populateCards() {
        cards.add(new CardMove(this, cardNameList[0], 0, false));
        cards.add(new CardPay(this, cardNameList[1], 50, false));
        cards.add(new CardMove(this, cardNameList[2], 15, false));
        cards.add(new CardMove(this, cardNameList[3], 7, false));
        cards.add(new CardPay(this, cardNameList[4], 50, false));
        cards.add(new CardPay(this, cardNameList[5], 150, false));
        cards.add(new CardMove(this, cardNameList[6], -3, true));
        cards.add(new CardMove(this, cardNameList[7], 6, false));
        cards.add(new CardPay(this, cardNameList[8], -15, false));
        cards.add(new CardMove(this, cardNameList[9], 23, false));
        cards.add(new CardPay(this, cardNameList[10], 50, true));
    }


    public int roll() {
        return (int) Math.round((Math.random() % 6) + 1);
    }

    public void getCard() {
        nextCard = nextCard >= cards.size() ? 0 : nextCard;

        cards.get(nextCard).onPull(players);

        nextCard++;
    }

    public void startGame() throws MissingArgumentException {
        var addedPlayers = 0;

        System.out.print("Monopoly Lite\n" +
                "Author: Ferenc Fodor\n\n" +
                "Max players: " + MAX_PLAYER +
                "(type \"help\" for commands)\n")
        ;

        while (addedPlayers < MAX_PLAYER) {
            var start = false;
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
                    System.out.println("Starting game...");
                    break;

                case "help":
                    printHelp();
                    break;
            }

            if (start) break;
        }

        populateFields();
        populateCards();


        update();

    }

    private void update() {
        Iterator<Player> it = players.iterator();


        while (players.size() != 1) {
            players.removeIf(x -> x.getMoney() <= 0);
            if (!it.hasNext())
                it = players.iterator();
            currentPlayer = it.next();


            if (currentPlayer.getInJail() > 0) {
                fields.get(currentPlayer.getFieldPos()).onStay();
                continue;
            }
            System.out.println(currentPlayer.getName() + "'s Turn:\n Press Enter to roll");

            try {
                var read = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

            rolledValue = roll();
            System.out.println(currentPlayer.getName() + " rolled " + rolledValue + ".");

            for (var i = 0; i < rolledValue; i++) {
                var pos = currentPlayer.getFieldPos() + 1;

                if (pos > 23)
                    pos = 0;

                fields.get(pos).onEnter();
                currentPlayer.setFieldPos(pos);
            }
            fields.get(currentPlayer.getFieldPos()).onStay();

        }
        System.out.print("WINNER: " + players.get(0).getName());
        scanner.close();
    }

    private void printHelp() {
        System.out.print("help - Usage: help\n\tprint help info.\n" +
                "add - Usage: add [playerName]\n\tAdds a player to game\n" +
                "remove - Usage: remove [playerName]\n\tRemoves player from game\n" +
                "start - Usage: start\n\tStarts the game");
    }
}
