package my.assignment.forthe.card.game.blackjack.gameClasses;

import android.util.Log;

import java.util.ArrayList;

public class Dealer {

    ArrayList<Card> cards;

    Dealer() {
        cards = new ArrayList<Card>();
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public int cardTotalPoints() {
        int total = 0;
        int numberOfAces = 0;

        for (Card card: cards) {
            if(card.getNumber() == 1){
                numberOfAces++;
                Log.i("cardtotalpoints", "numofaces" + numberOfAces);
            }
            else{
                total += card.getNumber();
                Log.i("cardtotalpoints", "total" + total);
            }
        }

        int leftValue = 21 - total;

        for(int i = 1; i <= numberOfAces; i++)
        {
            if(leftValue >= 11){
                total += 11;
            }else {
                total += 1;
            }
        }
        Log.i("cardtotalpoints", "totalend " + total);
        return total;
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public int numberOfCards()
    {
        return cards.size();
    }
}
