package com.example.arqtarea3_complete.asyntask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arqtarea3_complete.MainActivity;
import com.example.arqtarea3_complete.R;

public class IndependentAsyntaskClassAsyntaskActivity extends AppCompatActivity implements TaskListener {

    private static final String TAG = MainActivity.class.getName();
    private EditText inputField, resultField;
    private Button primecheckbutton;
    private MyAsyncTask mAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number_asyntask);
        inputField = (EditText) findViewById(R.id.inputField);
        resultField = (EditText) findViewById(R.id.resultField);
        primecheckbutton = (Button) findViewById(R.id.primecheckbutton);
    }

    public void triggerPrimecheck(View v) {
        if (!isRunning()) {
            Log.v(TAG, "Thread " + Thread.currentThread().getId() +
                    ": triggerPrimecheck() comienza");
            long parameter = Long.parseLong(inputField.getText().toString());
            mAsyncTask = new MyAsyncTask(this);
            mAsyncTask.execute(parameter);
            Log.v(TAG, "Threa   d " + Thread.currentThread().getId() +
                    ": triggerPrimecheck() termina");
        } else {
            Log.v(TAG, "Cancelando test " + Thread.currentThread().getId());
            mAsyncTask.cancel(true);
        }
    }

    private boolean isRunning() {
        return mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRunning()) {
            mAsyncTask.cancel(true);
        }
    }

    @Override
    public void onPreExecute() {
        resultField.setText("");
        primecheckbutton.setText("CANCELAR");
    }

    @Override
    public void onProgressUpdate(double progreso) {
        resultField.setText(String.format("%.1f%% completado", progreso*100));
    }

    @Override
    public void onPostExecute(boolean resultado) {
        resultField.setText(resultado + "");
        primecheckbutton.setText("¿ES PRIMO?");
    }

    @Override
    public void onCancelled() {
        resultField.setText("Proceso cancelado");
        primecheckbutton.setText("¿ES PRIMO?");
    }
}
