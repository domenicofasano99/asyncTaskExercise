package com.example.hw2_domenicofasano_721hhhinginfor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TimerTask extends AsyncTask<Integer, Integer, Void> {

    private WeakReference<TextView> textResultWeakReference;
    private int seconds;

    public TimerTask(TextView textTimerView) {
        this.textResultWeakReference = new WeakReference<>(textTimerView);
    }

    @Override
    protected Void doInBackground(Integer... params) {
        seconds = params[0];
        for (int i = 1; i <= seconds; i++) {
            SystemClock.sleep(1000);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        TextView textView = textResultWeakReference.get();
        textView.setTextColor(Color.BLACK);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText("Timer: " + values[0] + " seconds");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TextView textView = textResultWeakReference.get();
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.GREEN);
        textView.setText("Timer " + seconds + " seconds completed");
    }
}
