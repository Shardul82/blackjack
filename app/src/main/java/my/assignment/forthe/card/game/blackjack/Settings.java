package my.assignment.forthe.card.game.blackjack;
/***************************************************************
 *                                                             *
 * CSCI 524      Assignment 2 BlackJack      Spring 2021       *
 *                                                             *
 * Class Name:  Settings                                       *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *             Ashwanth                                        *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: Settings class is used to change the game settings *
 *          like number of decks and rules of game. It will    *
 *          display options and save button.                   *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Settings extends AppCompatActivity {

    Button saveButton;
    int noOfDeck;
    int ruleIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveButton = findViewById(R.id.save_button);
        loadSettingsFromSharedPreference();

        Spinner spinDeckSize;
        spinDeckSize = (Spinner) findViewById(R.id.spinNoDeck);//fetch the spinner from layout file
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.deck_array));//setting the country_array to spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDeckSize.setAdapter(adapter);
        spinDeckSize.setSelection(noOfDeck - 1);


        spinDeckSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                noOfDeck = position + 1;

                saveSettingsInSharedPreference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //
        Spinner spinGameRule;
        spinGameRule = (Spinner) findViewById(R.id.ruleGame);//fetch the spinner from layout file
        ArrayAdapter<String> adapterGame = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.rule_spinner));//setting the country_array to spinner
        adapterGame.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinGameRule.setAdapter(adapterGame);
        spinGameRule.setSelection(ruleIndex);


        spinGameRule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                ruleIndex = position;

                saveSettingsInSharedPreference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void loadSettingsFromSharedPreference(){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

        noOfDeck = settings.getInt("noOfDeck", noOfDeck);
        ruleIndex = settings.getInt("ruleIndex", ruleIndex);

        if(noOfDeck == 0){
            noOfDeck = 1;
        }
    }

    void saveSettingsInSharedPreference() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = this.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putInt("noOfDeck", noOfDeck);
        editor.putInt("ruleIndex", ruleIndex);

        editor.apply();
    }
}