package com.divyakhare.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etP1,etP2;
    TextView txtStatus;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    Button btnSubmit;

    String player1 ="Player 1",player2="Player 2";

    boolean gameActive = true;

    // Player representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    //    0 - X
    //    1 - O
    //    2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etP1=findViewById(R.id.etP1);
        etP2=findViewById(R.id.etP2);
        img1=findViewById(R.id.imgTopLeft);
        img2=findViewById(R.id.imgUp);
        img3=findViewById(R.id.imgTopRight);
        img4=findViewById(R.id.imgLeft);
        img5=findViewById(R.id.imgCenter);
        img6=findViewById(R.id.imgRight);
        img7=findViewById(R.id.imgBottomLeft);
        img8=findViewById(R.id.imgDown);
        img9=findViewById(R.id.imgBottomRight);
        txtStatus=findViewById(R.id.txtStatus);
        btnSubmit=findViewById(R.id.btnSubmit);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player1=etP1.getText().toString();
                player2=etP2.getText().toString();
                if (player1.isEmpty()||player2.isEmpty()){
                    etP1.setError("Enter name");
                    etP2.setError("Enter name");
                }else{
                    txtStatus.setText(player1);}
            }
        });
    }
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called
        // if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase the counter
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion
            // effect to the image
            img.setTranslationY(-1000f);

            // change the active player
            // from 0 to 1 or 1 to 0
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.multiply);
                activePlayer = 1;

                // change the status
                //o's turn
                txtStatus.setText(player2);
            } else {
                // set the image of o
                img.setImageResource(R.drawable.circle);
                activePlayer = 0;


                // change the status
                //x's turn
                txtStatus.setText(player1);
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if any player has won
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Somebody has won! - Find out who!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = player1+"-X has won";
                    alert(winnerStr);
                    gameReset(view);
                } else {
                    winnerStr = player2+"-O has won";
                    alert(winnerStr);
                    gameReset(view);
                }
                // Update the status bar for winner announcement
                txtStatus.setText(winnerStr);
                //txtStatus.setText(player1);
            }
        }
        // set the status if the match draw
        if (counter == 9 && flag == 0) {
            txtStatus.setText("Match Draw");
            gameReset(view);
        }
    }

    private void alert(String winner) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);
        // Set the message show for the Alert time
        builder.setMessage(winner);
        // Set Alert Title
        builder.setTitle("Congratulations!!");
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);
        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.
        builder.setPositiveButton(
                "Play Again", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which)
                    {
                        // When the user click yes button
                        // then app will close
                        dialog.cancel();
                    }
                });
        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder.setNegativeButton(
                "Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // If user click no
                        // then dialog box is canceled.
                        finish();
                    }
                });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }


    // reset the game
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        // remove all the images from the boxes inside the grid
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);
        img4.setImageResource(0);
        img5.setImageResource(0);
        img6.setImageResource(0);
        img7.setImageResource(0);
        img8.setImageResource(0);
        img9.setImageResource(0);


        txtStatus.setText(player1);
    }
}