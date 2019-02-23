package com.example.arqtarea3_complete.asyntask.extra2;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by William_ST on 18/02/19.
 */

public class MyAsyncTask extends AsyncTask<Long, Double, Boolean> {

    private final String TAG = MyAsyncTask.class.getName();
    private final TaskListener listener;

    public MyAsyncTask(TaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Long... n) {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": Comienza doInBackground()");
        long numComprobar = n[0];
        if (numComprobar < 2 || numComprobar % 2 == 0)
            return false;
        double limite = Math.sqrt(numComprobar) + 0.0001;
        double progreso = 0;
        for (long factor = 3; factor < limite && !isCancelled(); factor += 2) {
            if (numComprobar % factor == 0) return false;
            if (factor > limite * progreso / 100) {
                publishProgress(progreso / 100);
                progreso += 5;
            }
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Thread " + Thread.currentThread().getId() +
                ": Finaliza doInBackground()");
        return true;
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onPreExecute()");
        listener.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Double... progress) {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
        listener.onProgressUpdate(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean isPrime) {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onPostExecute()");
        listener.onPostExecute(isPrime);
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG, "Thread " + Thread.currentThread().getId() +
                ": onCancelled");
        super.onCancelled();
        listener.onCancelled();
    }
}
