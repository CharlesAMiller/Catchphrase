package xyz.charliemiller.catchphrase.catchphrase;

import android.support.v7.app.AlertDialog;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by charlie on 5/4/16.
 *
 * "This is a low flying panic attack" - Burn the Witch
 */
public class CatchphraseGameTimer extends CountDownTimer
{
    private static final String TAG = "CatchphraseGameTimer";

    private long beepInterval;
    private long lastBeep;

    private AlertDialog.Builder dialog;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CatchphraseGameTimer(long millisInFuture, long countDownInterval, AlertDialog.Builder d)
    {
        super(millisInFuture, countDownInterval);

        this.dialog = d;
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
        Log.d(TAG, "ms left: " + String.valueOf(millisUntilFinished));
    }

    @Override
    public void onFinish()
    {
        dialog.show();
    }
}
