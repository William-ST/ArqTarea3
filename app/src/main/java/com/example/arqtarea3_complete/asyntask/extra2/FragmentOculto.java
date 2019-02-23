package com.example.arqtarea3_complete.asyntask.extra2;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by William_ST on 18/02/19.
 */

public class FragmentOculto extends Fragment implements TaskListener {

    public static final String TAG = FragmentOculto.class.getName();

    private TaskListener taskListener;
    private MyAsyncTask myAsyncTask;
    private long numComprobar;

    public FragmentOculto() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle parameters = this.getArguments();
        if (parameters != null)
            this.numComprobar = parameters.getLong("numComprobar", 0);
        myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(this.numComprobar);
    }

    @Override
    public void onAttach(Activity actividad) {
        super.onAttach(actividad);
        try {
            this.taskListener = (TaskListener) actividad;
        } catch (ClassCastException ex) {
            Log.e(TAG, "El Activity debe implementar la interfaz TaskListener");
        }
    }

    public boolean isRunning() {
        return myAsyncTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    public void cancelAsyncTask() {
        myAsyncTask.cancel(true);
    }

    @Override
    public void onDetach() {
        this.taskListener = null;
        super.onDetach();
    }

    public static FragmentOculto newInstance(Bundle argumentos) {
        FragmentOculto f = new FragmentOculto();
        if (argumentos != null) {
            f.setArguments(argumentos);
        }
        return f;
    }

    @Override
    public void onPreExecute() {
        if (taskListener == null) return;

        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onPreExecute()");
        taskListener.onPreExecute();
    }

    @Override
    public void onProgressUpdate(double progreso) {
        if (taskListener == null) return;

        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
        taskListener.onProgressUpdate(progreso);
    }

    @Override
    public void onPostExecute(boolean isPrime) {
        if (taskListener == null) return;

        Log.v(TAG, "Thread" + Thread.currentThread().getId() + ": onPostExecute()");
        taskListener.onPostExecute(isPrime);
    }

    @Override
    public void onCancelled() {
        if (taskListener == null) return;

        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onCancelled");
        taskListener.onCancelled();
    }

    static interface TaskListener {
        void onPreExecute();

        void onProgressUpdate(double progreso);

        void onPostExecute(boolean resultado);

        void onCancelled();
    }

}
