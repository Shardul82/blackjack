package my.assignment.forthe.card.game.blackjack.gameClasses;

import android.content.Context;

import my.assignment.forthe.card.game.blackjack.gameClasses.enums.GAME_ERRORS;

public class Game {
    Dealer dealer;
    Gambler gambler;
    Deck deck;
    int betAmount = 100;

    public Game(Context context){
        dealer = new Dealer();
        gambler = new Gambler();
        deck = new Deck(context);
    }

    public GAME_ERRORS dealCards() {
        if(gambler.getMoney() < betAmount)
        {
            return GAME_ERRORS.NOT_ENOUGH_MONEY;
        }

        if(deck.deck.size() == 0){
            return GAME_ERRORS.NOT_ENOUGH_CARDS;
        }

        // give Dealer 2 cards
        dealer.takeCard(deck.getNextCardFromDeck());
        dealer.takeCard(deck.getNextCardFromDeck());

        // give Gambler 2 cards
        gambler.takeCard(deck.getNextCardFromDeck());
        gambler.takeCard(deck.getNextCardFromDeck());

        return GAME_ERRORS.NO_ERROR;
    }

    GAME_ERRORS clear() {
        // place your bet screen

        return GAME_ERRORS.NO_ERROR;
    }

    public Deck getDeck() {
        return deck;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Gambler getGambler() {
        return gambler;
    }
}
