package com.example.arqtarea3_complete.asyntask.extra2;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arqtarea3_complete.R;

public class ProgressbarActivity extends AppCompatActivity implements FragmentOculto.TaskListener, App.StatusAppInterface {

    private static final String TAG = ProgressbarActivity.class.getName();

    private static final String KEY_TEXT_BTN = "textValue";
    private static final String KEY_SHOW_DIALOG = "showDialog";
    private static final String KEY_MESSAGE_DIALOG = "messageDialog";

    private EditText inputField, resultField;
    private Button primecheckbutton;
    private static FragmentOculto hiddenFragment;
    private CharSequence progressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number_asyntask);
        inputField = (EditText) findViewById(R.id.inputField);
        resultField = (EditText) findViewById(R.id.resultField);
        primecheckbutton = (Button) findViewById(R.id.primecheckbutton);
        progress = new ProgressDialog(this);

        if (savedInstanceState != null) {
            CharSequence savedText = savedInstanceState.getCharSequence(KEY_TEXT_BTN);
            primecheckbutton.setText(savedText);
            if (savedInstanceState.getBoolean(KEY_SHOW_DIALOG)) {
                CharSequence messageDialog = savedInstanceState.getCharSequence(KEY_MESSAGE_DIALOG);
                progress.setMessage(messageDialog);
                progress.show();
            }
        }

        ((App) getApplication()).setStatusAppInterface(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_TEXT_BTN, primecheckbutton.getText());
        if (progress.isShowing()) {
            outState.putBoolean(KEY_SHOW_DIALOG, true);
            outState.putCharSequence(KEY_MESSAGE_DIALOG, progressMessage);
            progress.dismiss();
        }
    }

    public void triggerPrimecheck(View v) {
        if (hiddenFragment == null) {
            calculatePrimeNumber();
        } else {
            if (hiddenFragment.isRunning()) {
                hiddenFragment.cancelAsyncTask();
            } else {
                calculatePrimeNumber();
            }
        }
    }

    private void calculatePrimeNumber() {
        long parameter = Long.parseLong(inputField.getText().toString());
        Bundle parametros = new Bundle();
        parametros.putLong("numComprobar", parameter);
        FragmentOculto fragment = FragmentOculto.newInstance(parametros);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment, FragmentOculto.TAG);
        ft.commit();
        hiddenFragment = fragment;
    }

    private ProgressDialog progress;

    @Override
    public void onPreExecute() {
        resultField.setText("");
        primecheckbutton.setText("CANCELAR");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    @Override
    public void onProgressUpdate(double progreso) {
        //resultField.setText(String.format("%.1f%% completado", progreso * 100));
        progressMessage = String.format("%.1f%% completado", progreso * 100);
        progress.setMessage(progressMessage);
    }

    @Override
    public void onPostExecute(boolean resultado) {
        resultField.setText(resultado + "");
        primecheckbutton.setText("¿ES PRIMO?");
        progress.dismiss();
    }

    @Override
    public void onCancelled() {
        if (hiddenFragment != null) {
            Log.d(TAG, "onCancelled isAdded(): " + hiddenFragment.isAdded());
            resultField.setText("Proceso cancelado");
            primecheckbutton.setText("¿ES PRIMO?");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(hiddenFragment);
            ft.commitAllowingStateLoss();
            progress.dismiss();
        }
    }

    @Override
    public void inBackground() {
        Log.d(TAG, "inBackground hiddenFragment != null: " + (hiddenFragment != null));
        if (hiddenFragment != null) hiddenFragment.cancelAsyncTask();
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        if (iShowingSalveProgress) {
            progress.show();
            iShowingSalveProgress = false;
        }
    }
    */

    //private boolean iShowingSalveProgress;

}
