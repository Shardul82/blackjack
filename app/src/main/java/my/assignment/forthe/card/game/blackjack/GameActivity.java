package my.assignment.forthe.card.game.blackjack;
/***************************************************************
 *                                                             *
 * CSCI 524      Assignment 2 BlackJack      Spring 2021       *
 *                                                             *
 * Class Name:  GameActivity                                   *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *             Ashwanth                                        *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose:                                                    *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import my.assignment.forthe.card.game.blackjack.gameClasses.Card;
import my.assignment.forthe.card.game.blackjack.gameClasses.Game;

public class GameActivity extends AppCompatActivity {

    ImageView d1, d2, d3, d4, d5, d6, d7, d8, d9;
    ImageView g1, g2, g3, g4, g5, g6, g7, g8, g9;
    TextView betAmountTextView;
    int betAmount;
    Button standButton, doubleButton, hitButton, surrenderButton, saveStateButton;
    Button playAgain;
    TextView dealerScore, playerScore;
    Game game;
    ImageView gameResult;
    int totalMoney;
    boolean isLoadGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();
        game = new Game(this);

        Bundle bundle = getIntent().getExtras();
        betAmount = bundle.getInt("bet_amount");
        totalMoney = bundle.getInt("totalMoney");
        isLoadGame = bundle.getBoolean("isLoadGame");
        betAmountTextView.setText(betAmount + "");

        if(isLoadGame){
            loadGame();
            unSaveGameInSharedPreference();
        }else {
            startPlayingGame();
        }
    }

    void loadGame() {
        hideViews();
        // load n
        // load player n cards

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        betAmount = settings.getInt("betAmount", 1);
        betAmountTextView.setText(betAmount + "");
        int card0Num = settings.getInt("dealerCard0Num", 1);
        String card0Suit = settings.getString("dealerCard0Suit", "c");

        int imageId0 = this.getResources()
                .getIdentifier("card_" + card0Num + card0Suit, "drawable", getPackageName());

        Card card0 = new Card(card0Num, card0Suit, imageId0);
        game.getDeck().removeThisCardFromDeck(card0);
        game.getDealer().takeCard(card0);
        d1.setImageResource(R.drawable.cardback);
        showCard(d1);

        int card1Num = settings.getInt("dealerCard1Num", 2);
        String card1Suit = settings.getString("dealerCard1Suit", "c");
        int imageId1 = this.getResources()
                .getIdentifier("card_" + card1Num + card1Suit, "drawable", getPackageName());

        Card card1 = new Card(card1Num, card1Suit, imageId1);
        game.getDeck().removeThisCardFromDeck(card1);
        game.getDealer().takeCard(card1);
        d2.setImageResource(imageId1);
        showCard(d2);

        int numOfGamblerCards = settings.getInt("numOfGamblerCards", 2);

        for(int i = 0; i < numOfGamblerCards; i++){
            int cardNumGambler = settings.getInt("gamblerCard" + i + "Num", 2);
            String cardSuitGambler = settings.getString("gamblerCard" + i + "Suit", "c");
            int imageId0Gambler = this.getResources()
                    .getIdentifier("card_" + cardNumGambler + cardSuitGambler, "drawable", getPackageName());

            Card cardGambler = new Card(cardNumGambler, cardSuitGambler, imageId0Gambler);
            game.getDeck().removeThisCardFromDeck(cardGambler);
            game.getGambler().takeCard(cardGambler);

            String name = "g" + (i+ 1) + "";
            int id = getResources().getIdentifier(name, "id", getApplicationContext().getPackageName());
            ImageView view = findViewById(id);
            view.setImageResource(imageId0Gambler);
            showCard(view);

            dealerScore.setText(game.getDealer().cardTotalPoints() + "");
            playerScore.setText(game.getGambler().cardTotalPoints() + "");
            showButtons();
        }
    }

    void saveGameInSharedPreference() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putBoolean("isgamesaved", true);
        editor.putInt("betAmount", betAmount);
        editor.putInt("dealerCard0Num", game.getDealer().getCard(0).getNumber());
        editor.putString("dealerCard0Suit", game.getDealer().getCard(0).getSuit());

        editor.putInt("dealerCard1Num", game.getDealer().getCard(1).getNumber());
        editor.putString("dealerCard1Suit", game.getDealer().getCard(1).getSuit());


        editor.putInt("numOfGamblerCards", game.getGambler().numberOfCards());
        for(int i = 0; i < game.getGambler().numberOfCards(); i++){
            editor.putInt("gamblerCard" + i + "Num", game.getGambler().getCard(i).getNumber());
            editor.putString("gamblerCard" + i + "Suit", game.getGambler().getCard(i).getSuit());
        }

        editor.apply();

    }

    void unSaveGameInSharedPreference()
    {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putBoolean("isgamesaved", false);

        editor.apply();
    }

    void startPlayingGame() {
        hideViews();
        game.dealCards();

        // show two cards of each
        showCard(d1);

        loadSettingsFromSharedPreference();
        showCard(d2);
        if (ruleIndex == 0) {
            d2.setImageResource(game.getDealer().getCard(1).getImageId());
        }

        showCard(g1);
        g1.setImageResource(game.getGambler().getCard(0).getImageId());
        showCard(g2);
        g2.setImageResource(game.getGambler().getCard(1).getImageId());

        // dealer score
        dealerScore.setText("**" + "");
        playerScore.setText(game.getGambler().cardTotalPoints() + "");

        if (game.getGambler().cardTotalPoints() == 21) {   // blackjack first attempt
            if (game.getDealer().cardTotalPoints() == 21) {   // blackjack first attempt
                gameResult.setImageResource(R.drawable.tie);
            } else {
                gameResult.setImageResource(R.drawable.win);
                saveMoneyInSharedPreference(true, true, false);
                //Toast.makeText(GameActivity.this, "BlackJack!!!", Toast.LENGTH_SHORT).show();
            }
            showGameEndButtons();
        } else if (game.getGambler().cardTotalPoints() > 21) {
            // show 3 option to player
            gameResult.setImageResource(R.drawable.lose);
            // Toast.makeText(GameActivity.this, "Bust!!!", Toast.LENGTH_SHORT).show();
            saveMoneyInSharedPreference(false, false, false);
            showGameEndButtons();
        } else {
            showButtons();
        }
    }

    private void loadViews() {
        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        d6 = findViewById(R.id.d6);
        d7 = findViewById(R.id.d7);
        d8 = findViewById(R.id.d8);
        d9 = findViewById(R.id.d9);

        g1 = findViewById(R.id.g1);
        g2 = findViewById(R.id.g2);
        g3 = findViewById(R.id.g3);
        g4 = findViewById(R.id.g4);
        g5 = findViewById(R.id.g5);
        g6 = findViewById(R.id.g6);
        g7 = findViewById(R.id.g7);
        g8 = findViewById(R.id.g8);
        g9 = findViewById(R.id.g9);

        betAmountTextView = findViewById(R.id.bet_amount);
        standButton = findViewById(R.id.stand_button);
        doubleButton = findViewById(R.id.double_button);
        hitButton = findViewById(R.id.hit_button);
        surrenderButton = findViewById(R.id.surrender);
        saveStateButton = findViewById(R.id.save_state);

        dealerScore = findViewById(R.id.dealer_score);
        playerScore = findViewById(R.id.player_score);
        gameResult = findViewById(R.id.game_result);
    }

    void hideViews() {
        d1.setVisibility(View.INVISIBLE);
        d2.setVisibility(View.INVISIBLE);
        d3.setVisibility(View.INVISIBLE);
        d4.setVisibility(View.INVISIBLE);
        d5.setVisibility(View.INVISIBLE);
        d6.setVisibility(View.INVISIBLE);
        d7.setVisibility(View.INVISIBLE);
        d8.setVisibility(View.INVISIBLE);
        d9.setVisibility(View.INVISIBLE);

        g1.setVisibility(View.INVISIBLE);
        g2.setVisibility(View.INVISIBLE);
        g3.setVisibility(View.INVISIBLE);
        g4.setVisibility(View.INVISIBLE);
        g5.setVisibility(View.INVISIBLE);
        g6.setVisibility(View.INVISIBLE);
        g7.setVisibility(View.INVISIBLE);
        g8.setVisibility(View.INVISIBLE);
        g9.setVisibility(View.INVISIBLE);


        standButton.setVisibility(View.INVISIBLE);
        doubleButton.setVisibility(View.INVISIBLE);
        hitButton.setVisibility(View.INVISIBLE);
        surrenderButton.setVisibility(View.INVISIBLE);
        saveStateButton.setVisibility(View.INVISIBLE);

        playAgain = findViewById(R.id.play_again);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGameInSharedPreference();
                // Toast.makeText(GameActivity.this, "Game Saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        surrenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameResult.setImageResource(R.drawable.lose);
                saveMoneyInSharedPreference(false, false, true);
                showGameEndButtons();
            }
        });

        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(GameActivity.this, "Hit", Toast.LENGTH_SHORT).show();

                Card card = game.getDeck().getNextCardFromDeck();
                game.getGambler().takeCard(card);

                String name = "g" + game.getGambler().numberOfCards() + "";
                int id = getResources().getIdentifier(name, "id", getApplicationContext().getPackageName());
                ImageView view = findViewById(id);
                view.setImageResource(card.getImageId());
                showCard(view);


                //new score
                playerScore.setText(game.getGambler().cardTotalPoints() + "");
                if (game.getGambler().cardTotalPoints() == 21) {   // blackjack first attempt
                    if (game.getDealer().cardTotalPoints() == 21) {   // blackjack first attempt
                        gameResult.setImageResource(R.drawable.tie);
                    } else {
                        gameResult.setImageResource(R.drawable.win);
                        saveMoneyInSharedPreference(true, true, false);
                        //Toast.makeText(GameActivity.this, "BlackJack!!!", Toast.LENGTH_SHORT).show();
                    }
                    showGameEndButtons();
                } else if (game.getGambler().cardTotalPoints() > 21) {
                    // show 3 option to player
                    gameResult.setImageResource(R.drawable.lose);
                    saveMoneyInSharedPreference(false, false, false);
                    showGameEndButtons();
                } else {
                    showButtons();
                }
            }
        });

        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Toast.makeText(GameActivity.this, "stand", Toast.LENGTH_SHORT).show();
                if (game.getGambler().cardTotalPoints() > 21 && game.getDealer().cardTotalPoints() > 21) {
                    gameResult.setImageResource(R.drawable.tie);
                    showGameEndButtons();
                } else if (game.getGambler().cardTotalPoints() == 21) {   // blackjack first attempt
                    if (game.getDealer().cardTotalPoints() == 21) {   // blackjack first attempt
                        gameResult.setImageResource(R.drawable.tie);
                    } else {
                        gameResult.setImageResource(R.drawable.win);
                        saveMoneyInSharedPreference(true, true, false);
                        //Toast.makeText(GameActivity.this, "BlackJack!!!", Toast.LENGTH_SHORT).show();
                    }
                    showGameEndButtons();
                } else {
                    while (game.getDealer().cardTotalPoints() < 16) {
                        Card card = game.getDeck().getNextCardFromDeck();
                        game.getDealer().takeCard(card);

                        String name = "d" + game.getDealer().numberOfCards() + "";
                        int id = getResources().getIdentifier(name, "id", getApplicationContext().getPackageName());
                        ImageView view = findViewById(id);
                        view.setImageResource(card.getImageId());
                        showCard(view);
                    }

                    if (game.getDealer().cardTotalPoints() > 21) {
                        //       Toast.makeText(GameActivity.this, "Bust", Toast.LENGTH_SHORT).show();
                        gameResult.setImageResource(R.drawable.win);
                        saveMoneyInSharedPreference(true, false, false);
                    } else if (game.getGambler().cardTotalPoints() > game.getDealer().cardTotalPoints()) {
                        gameResult.setImageResource(R.drawable.win);
                        saveMoneyInSharedPreference(true, false, false);
                    } else if (game.getGambler().cardTotalPoints() < game.getDealer().cardTotalPoints()) {
                        gameResult.setImageResource(R.drawable.lose);
                        saveMoneyInSharedPreference(false, false, false);
                    } else if (game.getGambler().cardTotalPoints() == game.getDealer().cardTotalPoints()) {
                        gameResult.setImageResource(R.drawable.tie);
                    }
                    showGameEndButtons();
                }

            }
        });

        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                betAmount = betAmount * 2;
                betAmountTextView.setText(betAmount + "");

                // bet amount
                Card card = game.getDeck().getNextCardFromDeck();
                game.getGambler().takeCard(card);

                String name = "g" + game.getGambler().numberOfCards() + "";
                int id = getResources().getIdentifier(name, "id", getApplicationContext().getPackageName());
                ImageView view = findViewById(id);
                view.setImageResource(card.getImageId());
                showCard(view);


                //new score
                playerScore.setText(game.getGambler().cardTotalPoints() + "");
                if (game.getGambler().cardTotalPoints() == 21) {   // blackjack first attempt
                    if (game.getDealer().cardTotalPoints() == 21) {   // blackjack first attempt
                        gameResult.setImageResource(R.drawable.tie);
                    } else {
                        gameResult.setImageResource(R.drawable.win);
                        saveMoneyInSharedPreference(true, false, false);
                        //Toast.makeText(GameActivity.this, "BlackJack!!!", Toast.LENGTH_SHORT).show();
                    }
                    showGameEndButtons();
                } else if (game.getGambler().cardTotalPoints() > 21) {
                    // show 3 option to player
                    gameResult.setImageResource(R.drawable.lose);
                    saveMoneyInSharedPreference(false, false, false);
                    showGameEndButtons();
                } else if (game.getGambler().cardTotalPoints() > game.getDealer().cardTotalPoints()) {
                    gameResult.setImageResource(R.drawable.lose);
                    saveMoneyInSharedPreference(false, false, false);
                    showGameEndButtons();
                } else if (game.getGambler().cardTotalPoints() < game.getDealer().cardTotalPoints()) {
                    gameResult.setImageResource(R.drawable.lose);
                    saveMoneyInSharedPreference(false, false, false);
                    showGameEndButtons();
                }
            }
        });
    }

    void saveMoneyInSharedPreference(boolean won, boolean blackjack, boolean surrender) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        editor = settings.edit();

        if (surrender) {
            totalMoney = totalMoney - ((int) (betAmount / 2));
        } else if (blackjack) {
            totalMoney = totalMoney + ((int) (1.5 * betAmount));
        } else if (won) {
            totalMoney = totalMoney + betAmount;
        } else {
            totalMoney = totalMoney - betAmount;
        }
        if (totalMoney > 0) {
            editor.putInt("totalMoney", totalMoney);

        } else {
            editor.putInt("totalMoney", 0);
            editor.apply();
            Intent intent = new Intent(GameActivity.this, GameOver.class);
            startActivity(intent);
            finish();
        }

        editor.apply();
    }


    void showButtons() {
        standButton.setVisibility(View.VISIBLE);
        doubleButton.setVisibility(View.VISIBLE);
        hitButton.setVisibility(View.VISIBLE);
        surrenderButton.setVisibility(View.VISIBLE);
        saveStateButton.setVisibility(View.VISIBLE);
    }

    void hideButtons() {
        standButton.setVisibility(View.INVISIBLE);
        doubleButton.setVisibility(View.INVISIBLE);
        hitButton.setVisibility(View.INVISIBLE);
        surrenderButton.setVisibility(View.INVISIBLE);
        saveStateButton.setVisibility(View.INVISIBLE);
    }

    void showGameEndButtons() {
        hideButtons();
        gameResult.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);

        d1.setImageResource(game.getDealer().getCard(0).getImageId());
        d2.setImageResource(game.getDealer().getCard(1).getImageId());
        dealerScore.setText(game.getDealer().cardTotalPoints() + "");
    }


    void showCard(ImageView card) {
        card.setVisibility(View.VISIBLE);
    }

    void hideCard(ImageView card) {
        card.setVisibility(View.INVISIBLE);
    }

    int ruleIndex;

    void loadSettingsFromSharedPreference() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        ruleIndex = settings.getInt("ruleIndex", ruleIndex);
    }
}