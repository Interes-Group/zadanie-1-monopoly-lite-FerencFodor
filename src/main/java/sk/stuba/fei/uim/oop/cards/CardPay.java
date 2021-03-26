package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.GameSystem;
import sk.stuba.fei.uim.oop.Player;

import java.util.List;

public class CardPay extends Card {
    private boolean doTransaction;

    public CardPay(GameSystem system, String cardText, int value, boolean doTransaction) {
        super(system, cardText, value);
        this.doTransaction = doTransaction;
    }

    @Override
    public void onPull(List<Player> playerList) {
        System.out.println(cardText);

        if (doTransaction)
            transaction(playerList);
        else
            payment();
    }

    private void payment() {
        var money = system.currentPlayer.getMoney();
        system.currentPlayer.setMoney(money + value);
    }

    private void transaction(List<Player> playersToPay) {
        var money = system.currentPlayer.getMoney();
        money = money + (value * playersToPay.size());

        system.currentPlayer.setMoney(money);

        playersToPay.forEach(x -> x.setMoney(x.getMoney() - value));
    }
}
