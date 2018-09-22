package com.games.norbert.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button[][] buttons = new Button[3][3];
    private Button btnReset;
    private TextView tvPlayerOne, tvPlayerTwo;
    private boolean playerOneTurn = true;
    private int roundCount, playerOnePoints, playerTwoPoints, resID;
    private String[][] field;
    private String buttonID;

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPlayerOne = (TextView) findViewById(R.id.tvPlayerOne);
        tvPlayerTwo = (TextView) findViewById(R.id.tvPlayerTwo);
        btnReset = (Button) findViewById(R.id.btnReset);

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                buttonID = "btn" + i + j;
                resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                resetGame();
            }
        });
    }

    @Override public void onClick(View v)
    {
        if (!((Button) v).getText().toString().equals("")) return;

        if (playerOneTurn) ((Button)v).setText("X");
        else               ((Button)v).setText("O");

        roundCount++;

        if (checkForWin())
        {
            if (playerOneTurn) playerOneWins();
            else               playerTwoWins();
        }
        else if (roundCount==9) draw();
        else playerOneTurn = !playerOneTurn;
    }

    private boolean checkForWin()
    {
        field = new String[3][3];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = buttons[i][j].getText().toString();

        // check each row
        for (int i = 0; i < 3; i++)
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(""))
                return true;

        // check each column
        for (int i = 0; i < 3; i++)
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(""))
                return true;

        // check top-left to bottom-right diagonal
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals(""))
            return true;

        // check top-right to bottom-left diagonal
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals(""))
            return true;

        // if none of the above cases are true, return false, which means
        // that no one has won and the game must keep going
        return false;
    }

    private void playerOneWins()
    {
        playerOnePoints++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerTwoWins()
    {
        playerTwoPoints++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw()
    {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText()
    {
        tvPlayerOne.setText("Player 1: " + playerOnePoints);
        tvPlayerTwo.setText("Player 2: " + playerTwoPoints);
    }

    private void resetBoard()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText("");

        roundCount=0;
        playerOneTurn=true;
    }

    private void resetGame()
    {
        playerOnePoints=0;
        playerTwoPoints=0;
        updatePointsText();
        resetBoard();
    }

    @Override protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints", playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }
}