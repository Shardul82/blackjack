package my.assignment.forthe.card.game.blackjack.gameClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;


public class Deck {
    ArrayList<Card> deck;
    Context context;
    int noOfDeck;

    Deck(Context context) {
        this.context = context;
        deck = new ArrayList<Card>();
        loadDeck();
        shuffleDeck();
    }

    public Card getNextCardFromDeck() {
        if (noOfCardsInDeck() == 0)
            return null;
        return deck.remove(deck.size() - 1);
    }

    public void removeThisCardFromDeck(Card card){
        deck.remove(card);
    }

    int noOfCardsInDeck() {
        return deck.size();
    }

    void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private void loadDeck() {
        loadSettingsFromSharedPreference();
        String [] suits = {"c", "s", "d", "h"};

        for(int j = 0; j < noOfDeck; j++) {
            for (int k = 0; k < suits.length; k++) {
                for (int i = 1; i <= 13; i++) {
                    int imageId = context.getResources()
                            .getIdentifier("card_" + i + suits[k], "drawable", context.getPackageName());

                    Card card = new Card(i, suits[k], imageId);
                    deck.add(card);
                    Log.i("card", card.toString());
                }
            }
        }
    }

    void loadSettingsFromSharedPreference(){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        noOfDeck = settings.getInt("noOfDeck", noOfDeck);

        if(noOfDeck == 0){
            noOfDeck = 1;
        }
    }
}
