package my.assignment.forthe.card.game.blackjack;
/***************************************************************
 *                                                             *
 * CSCI 524      Assignment 2 BlackJack      Spring 2021       *
 *                                                             *
 * Class Name:  SplashScreen                                   *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *             Ashwanth                                        *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: SplashScreen is first screen where it will display *
 *          BlackJack logo and it will direct the screen to    *
 *          main activity.                                     *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}