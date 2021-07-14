package my.assignment.forthe.card.game.blackjack;
/***************************************************************
 *                                                             *
 * CSCI 524      Assignment 2 BlackJack      Spring 2021       *
 *                                                             *
 * Class Name:  MainActivity                                   *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *             Ashwanth                                        *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: MainActivity is a main class, it has onCreate      *
 *         method which calls when we start the app and it     *
 *         creates an intent that will start the next activity.*                                                    *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int bet_amount = 0;
    int totalMoney = 0;
    Button button_1, button_10, button_25, button_50, button_100;
    Button settingButton;
    Button loadSavedState;

    ImageButton cancelBidButton;
    TextView cancelButtonUnderText;
    ImageButton acceptBidButton;
    TextView acceptButtonUnderText;
    TextView betAmountTextView, betAmountTextViewUnderText;

    TextView totalMoneyTextView;

    /*****************************************************************
     *                                                               *
     * Method Name:  onCreate                                        *
     *                                                               *
     *                                                               *
     * Purpose: The onCreate method is used to initialize activity   *
     *          here and intent is created to move to next activity  *
     *          GameActivity.                                        *
     *****************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMoneyFromSharedPreference();

        // buttons used for different bet values and other buttons
        button_1 = findViewById(R.id.button_1_chip);
        button_10 = findViewById(R.id.button_10_chip);
        button_25 = findViewById(R.id.button_25_chip);
        button_50 = findViewById(R.id.button_50_chip);
        button_100 = findViewById(R.id.button_100_chip);
        settingButton = findViewById(R.id.setting_button);
        loadSavedState = findViewById(R.id.load_saved_state);
        if(!isGameSavedInSharedPreference()){
            loadSavedState.setVisibility(View.INVISIBLE);
        }

        cancelBidButton = findViewById(R.id.cancelBidButton);
        acceptBidButton = findViewById(R.id.acceptBidButton);

        cancelButtonUnderText = findViewById(R.id.cancel_button_under_text);
        acceptButtonUnderText = findViewById(R.id.accept_bid_under_text);
        betAmountTextView = findViewById(R.id.bet_amount);
        betAmountTextViewUnderText = findViewById(R.id.bet_amount_under_text);
        totalMoneyTextView = findViewById(R.id.total_money);

        button_1.setOnClickListener(this::onClick);
        button_10.setOnClickListener(this::onClick);
        button_25.setOnClickListener(this::onClick);
        button_50.setOnClickListener(this::onClick);
        button_100.setOnClickListener(this::onClick);
        cancelBidButton.setOnClickListener(this::onClick);
        acceptBidButton.setOnClickListener(this::onClick);
        settingButton.setOnClickListener(this::onClick);
        loadSavedState.setOnClickListener(this::onClick);
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  hideViews                                       *
     *                                                               *
     *                                                               *
     * Purpose: The hideViews method is used to hide the buttons.    *
     *****************************************************************/
    private void hideViews() {
        cancelBidButton.setVisibility(View.INVISIBLE);
        acceptBidButton.setVisibility(View.INVISIBLE);

        cancelButtonUnderText.setVisibility(View.INVISIBLE);
        acceptButtonUnderText.setVisibility(View.INVISIBLE);
        betAmountTextView.setVisibility(View.INVISIBLE);
        betAmountTextViewUnderText.setVisibility(View.INVISIBLE);
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  showViews                                       *
     *                                                               *
     *                                                               *
     * Purpose: The showViews method is used to show the buttons.    *
     *****************************************************************/
    private void showViews() {
        cancelBidButton.setVisibility(View.VISIBLE);
        acceptBidButton.setVisibility(View.VISIBLE);

        cancelButtonUnderText.setVisibility(View.VISIBLE);
        acceptButtonUnderText.setVisibility(View.VISIBLE);
        betAmountTextView.setVisibility(View.VISIBLE);
        betAmountTextViewUnderText.setVisibility(View.VISIBLE);
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  updateBetAmountAndMoney                         *
     *                                                               *
     *                                                               *
     * Purpose: The updateBetAmountAndMoney method is used to update *
     *          the bet amount and also the total amount.            *
     *****************************************************************/
    private void updateBetAmountAndMoney(){
        betAmountTextView.setText(bet_amount + "");
        totalMoneyTextView.setText(totalMoney + "");
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  shotToast                                       *
     *                                                               *
     *                                                               *
     * Purpose: The shotToast method is used to show the toast       *
     *          message when there is insufficient money.            *
     *****************************************************************/
    private void shotToast(){
        Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show();
    }
    // Switch case is used to perform action on the selected button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.button_1_chip:

                if(bet_amount + 1 > totalMoney){
                    shotToast();
                    return;
                }

                bet_amount += 1;
                showViews();
                updateBetAmountAndMoney();
                break;
            case R.id.button_10_chip:

                if(bet_amount + 10 > totalMoney){
                    shotToast();
                    return;
                }

                bet_amount += 10;
                showViews();
                updateBetAmountAndMoney();
                break;
            case R.id.button_25_chip:
                if(bet_amount + 25 > totalMoney){
                    shotToast();
                    return;
                }

                bet_amount += 25;
                showViews();
                updateBetAmountAndMoney();
                break;
            case R.id.button_50_chip:
                if(bet_amount + 50 > totalMoney){
                    shotToast();
                    return;
                }
                bet_amount += 50;
                showViews();
                updateBetAmountAndMoney();
                break;
            case R.id.button_100_chip:
                if(bet_amount + 100 > totalMoney){
                    shotToast();
                    return;
                }
                bet_amount += 100;
                showViews();
                updateBetAmountAndMoney();
                break;
            case R.id.cancelBidButton:
                bet_amount = 0;
                hideViews();
                updateBetAmountAndMoney();
                break;
            case R.id.acceptBidButton:
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("bet_amount", bet_amount);
                intent.putExtra("totalMoney", totalMoney);
                intent.putExtra("isLoadGame", false);
                startActivity(intent);
                finish();
                break;

            case R.id.setting_button:
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);
                break;

            case R.id.load_saved_state:
                Intent in = new Intent(MainActivity.this, GameActivity.class);
                in.putExtra("isLoadGame", true);
                startActivity(in);
                finish();
                break;
            default:
                break;
        }
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  getMoneyFromSharedPreference                    *
     *                                                               *
     *                                                               *
     * Purpose: The getMoneyFromSharedPreference method is used to   *
     *          display the total money.                             *
     *****************************************************************/
    void getMoneyFromSharedPreference(){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        totalMoney = settings.getInt("totalMoney", totalMoney);

        if(totalMoney == 0){
            totalMoney = 1000;
        }
        totalMoneyTextView = findViewById(R.id.total_money);
        totalMoneyTextView.setText(totalMoney + "");
    }

    /*****************************************************************
     *                                                               *
     * Method Name:  isGameSavedInSharedPreference                   *
     *                                                               *
     *                                                               *
     * Purpose: The isGameSavedInSharedPreference method is used to  *
     *          save the game.                                       *
     *****************************************************************/
    boolean isGameSavedInSharedPreference(){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        return settings.getBoolean("isgamesaved", false);
    }
}