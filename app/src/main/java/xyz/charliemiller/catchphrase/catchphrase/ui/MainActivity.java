package xyz.charliemiller.catchphrase.catchphrase.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import xyz.charliemiller.catchphrase.catchphrase.CatchphraseGameTimer;
import xyz.charliemiller.catchphrase.catchphrase.PreferenceHelper;
import xyz.charliemiller.catchphrase.catchphrase.R;
import xyz.charliemiller.catchphrase.catchphrase.tasks.GetKeyTask;

/** Catchphrase Game.
 *  Charlie "Porthog" Miller
 *  05/04/2016
 */
public class MainActivity extends AppCompatActivity
{

    /** */
    private static final String TAG = "MainActivity";

    /** Max number of skips. Retrieved value. */
    private int numberOfSkips;

    /** Player's current number of remaining skips */
    private int skipsRemaining;

    /** Round duration in ms. Retrieved value. */
    private long roundTime;

    /** Current number of points for each team
     * TODO: Maybe change this to accomodate any number of teams? */
    private int team1Points;
    private int team2Points;

    /** Default points needed to win. Retrieved value. */
    private int pointsNeededToWin; //

    private ArrayList<String> curCategories;
    private ArrayList<String> curTerms;
    private ArrayList<String> previousTerms;
    private String curTerm;

    private TextView mCurrentTermText;
    private TextView mRemainingSkipsText;
    private AlertDialog.Builder dialog;

    private boolean isPaused;
    private long timeRemaining;
    private CatchphraseGameTimer gameTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set activity content

        /* Setup toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurrentTermText = (TextView) findViewById(R.id.currentTerm);
        mRemainingSkipsText = (TextView) findViewById(R.id.skipsRemaining);

        /* Next button setup */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new FloatingActionButton.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(skipsRemaining > 0)
                {
                    getNewTerm();
                    skipsRemaining--;
                    if(mRemainingSkipsText != null)
                    {
//                        mRemainingSkipsText.setText(skipsRemaining);
                    }
                }
            }
        });

        dialog = new AlertDialog.Builder(this);
        dialog.setView(R.layout.score_dialog);
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                team1Points++;
            }

        });

        init(); // Setup

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) //TODO change to play button in game.
        {
            if(isPaused)
            {
                gameTimer.start();
            }
            else
            {
                gameTimer.cancel();
            }

            isPaused = !isPaused; // Switch

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        PreferenceHelper.setPreferences(getPreferences(MODE_PRIVATE));

        if(PreferenceHelper.getKey() == null)
        {
            GetKeyTask task = new GetKeyTask();
            task.execute(this);
        }

        numberOfSkips = PreferenceHelper.getNumberOfSkips();
        skipsRemaining = numberOfSkips;

        roundTime = PreferenceHelper.getRoundtime();

        team1Points = 0;
        team2Points = 0;
        pointsNeededToWin = PreferenceHelper.getNumberOfRoundsToWin();

        previousTerms = new ArrayList<>();
        curCategories = PreferenceHelper.getCategories();

        curTerms = new ArrayList<>();

        Log.d(TAG, "Getting Categories");
        for(String category : curCategories)
        {
            curTerms.addAll(PreferenceHelper.getTerms(category));
            Log.d(TAG, "Category: " + category);
        }

        gameTimer = new CatchphraseGameTimer(roundTime, 1000, dialog);

        isPaused = true;
    }

    private void getNewTerm()
    {
        if(curTerms.size() == previousTerms.size())
            previousTerms.clear();

        Log.d(TAG, "Number of skips: " + skipsRemaining);
        Random rand = new Random();

        int rIndex = rand.nextInt(curTerms.size());
        String interimTerm = curTerms.get(rIndex);

        while(previousTerms.contains(interimTerm))
        {
            rIndex = rand.nextInt(curTerms.size());
            interimTerm = curTerms.get(rIndex);
        }

        curTerm = interimTerm;

        if(mCurrentTermText != null)
        {
            mCurrentTermText.setText(curTerm);
        }
    }

}
