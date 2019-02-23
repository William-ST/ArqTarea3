package com.example.arqtarea3_complete.asyntask.extra5;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by William_ST on 18/02/19.
 */

public class MyAsyncTask extends AsyncTask<Long, Long, Void> {

    private final String TAG = MyAsyncTask.class.getName();
    private final TaskListener listener;

    public MyAsyncTask(TaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Long... n) {
        long startInterval = n[0];
        long endInterval = n[1];
        //long number = endInterval-startInterval;
        while (startInterval <= endInterval && !isCancelled()) {
            //final long number = endInterval-startInterval;
            if (isPrimeNumber(startInterval)) {
                publishProgress(startInterval);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startInterval++;
        }
        return null;
    }

    private boolean isPrimeNumber(long numComprobar) {
        if (numComprobar == 2) return true;
        if (numComprobar < 2 || numComprobar % 2 == 0)
            return false;
        double limite = Math.sqrt(numComprobar) + 0.0001;
        for (long factor = 3; factor < limite && !isCancelled(); factor += 2) {
            if (numComprobar % factor == 0) return false;
        }
        return true;
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onPreExecute()");
        listener.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Long... primeNumber) {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
        listener.onProgressUpdate(primeNumber[0]);
    }

    @Override
    protected void onPostExecute(Void avoid) {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onPostExecute()");
        listener.onPostExecute();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() +
                ": onCancelled");
        super.onCancelled();
        listener.onCancelled();
    }
}
