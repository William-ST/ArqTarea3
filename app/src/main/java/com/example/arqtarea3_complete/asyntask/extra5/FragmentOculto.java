package com.example.arqtarea3_complete.asyntask.extra5;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by William_ST on 18/02/19.
 */

public class FragmentOculto extends Fragment implements TaskListener {

    public static final String TAG = FragmentOculto.class.getName();

    private TaskListener taskListener;
    private MyAsyncTask[] myAsyncTasks;
    private long parameterStart, parameterEnd;
    private int asyntaskCount;

    public FragmentOculto() {
    }

    private void processInterval(long parameterStart, long parameterEnd) {
        if (parameterEnd >= parameterStart) {
            List<Long> numbers = new ArrayList<>();
            while (parameterStart <= parameterEnd) {
                numbers.add(parameterStart);
                parameterStart++;
            }

            int count = 0;
            Log.d(TAG, "numbers: " + numbers);
            Log.d(TAG, "numbers.size: " + numbers.size());
            Log.d(TAG, "myAsyncTasks: " + myAsyncTasks.length);
            Log.d(TAG, "numbers.size() / myAsyncTasks.length: " + (double) numbers.size() / (double) myAsyncTasks.length);

            double presize = (double) numbers.size() / (double) myAsyncTasks.length;
            Log.d(TAG, "presize: " + presize);
            int size = (int) presize;
            Log.d(TAG, "size - 1: " + size);
            if (presize > size) {
                size++;
                Log.d(TAG, "size++: " + size);
            }
            Log.d(TAG, "size - 2: " + size);

            for (int start = 0; start < numbers.size(); start += size) {
                int end = Math.min(start + size, numbers.size());
                List<Long> sublist = numbers.subList(start, end);
                myAsyncTasks[count] = new MyAsyncTask(this);
                Log.d(TAG, "start asyntask..." + count + "... start: " + sublist.get(0) + " end: " + sublist.get(sublist.size() - 1));
                //myAsyncTasks[count].execute(sublist.get(0), sublist.get(sublist.size() - 1));
                myAsyncTasks[count].executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, sublist.get(0), sublist.get(sublist.size() - 1));
                count++;
            }
            asyntaskCount = count;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle parameters = this.getArguments();
        if (parameters != null) {
            this.parameterStart = parameters.getLong("parameterStart", 0);
            this.parameterEnd = parameters.getLong("parameterEnd", 0);
        }
        asyntaskCount = 0;
        final int numOfAsyntask = getNumOfAsyntask();
        myAsyncTasks = new MyAsyncTask[numOfAsyntask];
        processInterval(parameterStart, parameterEnd);
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
        Log.d(TAG, "myAsyncTasks.length: " + myAsyncTasks.length);
        for (int i = 0; i < myAsyncTasks.length; i++) {
            Log.d(TAG, "i: " + i + " value: " + myAsyncTasks[i]);
            if (myAsyncTasks != null && myAsyncTasks[i] != null && myAsyncTasks[i].getStatus() == AsyncTask.Status.RUNNING) {
                return true;
            }
        }
        return false;
    }

    public void cancelAsyncTask() {
        for (int i = 0; i < myAsyncTasks.length; i++) {
            if (myAsyncTasks != null && myAsyncTasks[i] != null) myAsyncTasks[i].cancel(true);
        }
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
    public void onProgressUpdate(long primeNumber) {
        if (taskListener == null) return;

        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onProgressUpdate()");
        taskListener.onProgressUpdate(primeNumber);
    }

    @Override
    public void onPostExecute() {
        if (taskListener == null) return;

        Log.v(TAG, "Thread" + Thread.currentThread().getId() + ": onPostExecute()");
        asyntaskCount--;
        if (asyntaskCount == 0) {
            taskListener.onPostExecute();
        }
    }

    @Override
    public void onCancelled() {
        if (taskListener == null) return;

        Log.v(TAG, "Thread " + Thread.currentThread().getId() + ": onCancelled");
        asyntaskCount--;
        if (asyntaskCount == 0) {
            taskListener.onCancelled();
        }
    }

    static interface TaskListener {
        void onPreExecute();

        void onProgressUpdate(long primeNumber);

        void onPostExecute();

        void onCancelled();
    }

    private int getNumOfAsyntask() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getNumOfCores() + 1;
        }
        return getNumOfCores() + 1;
    }

    private int getNumOfCores() {
        try {
            int i = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File params) {
                    return Pattern.matches("cpu[0-9]", params.getName());
                }
            }).length;
            return i;
        } catch (Exception e) {
            Log.e(TAG, "Error determinando el número de procesadores");
        }
        return 1;
    }

}
