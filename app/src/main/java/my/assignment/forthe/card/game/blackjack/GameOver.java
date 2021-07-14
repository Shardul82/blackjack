package my.assignment.forthe.card.game.blackjack;
/***************************************************************
 *                                                             *
 * CSCI 524      Assignment 2 BlackJack      Spring 2021       *
 *                                                             *
 * Class Name:  GameOver                                       *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *             Ashwanth                                        *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: GameOver class is called when the game gets over,  *
 *          It will also have Play Again button if user wants  *
 *          to play another game.                              *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {

    Button playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = findViewById(R.id.play_again);
        // OnClickListener is used to navigate screen to main activity after clicking play again button
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}