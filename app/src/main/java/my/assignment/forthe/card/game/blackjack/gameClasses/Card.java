package my.assignment.forthe.card.game.blackjack.gameClasses;


import androidx.annotation.NonNull;

public class Card {
    private int number;
    String suit;
    int imageId;



    public Card(int number, String suit, int imageId)
    {
        this.number = number;
        this.suit = suit;
        this.imageId = imageId;
    }

    public int getNumber() {
        return number;
    }

    public String getSuit() {
        return suit;
    }

    public int getImageId() {
        return imageId;
    }

    @NonNull
    @Override
    public String toString() {
        return "card is " + number + " of " + suit  + ".";
    }
}

