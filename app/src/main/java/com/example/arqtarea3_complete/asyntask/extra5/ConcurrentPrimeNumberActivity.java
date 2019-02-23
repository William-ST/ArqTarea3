package com.example.arqtarea3_complete.asyntask.extra5;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arqtarea3_complete.R;
import com.example.arqtarea3_complete.asyntask.extra2.App;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentPrimeNumberActivity extends AppCompatActivity implements FragmentOculto.TaskListener, App.StatusAppInterface {

    private static final String TAG = ConcurrentPrimeNumberActivity.class.getName();

    private static final String KEY_SALVE_DATA = "salveData";
    private static final String KEY_TEXT_BTN = "textValue";
    private static final String KEY_LIST = "textValue";
    private static final String KEY_SHOW_DIALOG = "showDialog";
    private static final String KEY_MESSAGE_DIALOG = "messageDialog";

    private RecyclerView rvResult;
    private ResultAdapter resultAdapter;
    private EditText inputFieldStart, inputFieldEnd;
    private static FragmentOculto hiddenFragment;
    private List<String> savedResultList;
    private Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_extra4);
        btnCalculate = findViewById(R.id.primecheckbutton);
        rvResult = findViewById(R.id.rv_result);
        inputFieldStart = findViewById(R.id.input_field_start);
        inputFieldEnd = findViewById(R.id.input_field_end);

        //progress = new ProgressDialog(this);
        savedResultList = new ArrayList<>();
        if (savedInstanceState != null) {
            SavedData savedData = savedInstanceState.getParcelable(KEY_SALVE_DATA);
            btnCalculate.setText(savedData.getPrimecheckbuttonText());
            savedResultList = savedData.getSavedResultList();
            /*
            savedResultList = savedInstanceState.getStringArrayList(KEY_LIST);
            CharSequence savedText = savedInstanceState.getCharSequence(KEY_TEXT_BTN);
            primecheckbutton.setText(savedText);
            */
            /*
            if (savedInstanceState.getBoolean(KEY_SHOW_DIALOG)) {
                CharSequence messageDialog = savedInstanceState.getCharSequence(KEY_MESSAGE_DIALOG);
                progress.setMessage(messageDialog);
                progress.show();
            }
            */
        }

        initializeList(false);

        ((App) getApplication()).setStatusAppInterface(this);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerPrimecheck();
            }
        });
    }

    private void initializeList(boolean reset) {
        if (reset)
            resultAdapter = new ResultAdapter(this, new ArrayList<String>());
        else
            resultAdapter = new ResultAdapter(this, savedResultList);
        rvResult.setAdapter(resultAdapter);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putStringArrayList(KEY_LIST, savedResultList);
        //outState.putCharSequence(KEY_TEXT_BTN, primecheckbutton.getText());
        outState.putParcelable(KEY_SALVE_DATA, new SavedData(resultAdapter.getResultList(), btnCalculate.getText().toString()));
        /*
        if (progress.isShowing()) {
            outState.putBoolean(KEY_SHOW_DIALOG, true);
            outState.putCharSequence(KEY_MESSAGE_DIALOG, progressMessage);
            progress.dismiss();
        }
        */
    }

    private void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void triggerPrimecheck() {

        hideKeyboard();

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
        long parameterStart = Long.parseLong(inputFieldStart.getText().toString());
        long parameterEnd = Long.parseLong(inputFieldEnd.getText().toString());
        Bundle parametros = new Bundle();
        parametros.putLong("parameterStart", parameterStart);
        parametros.putLong("parameterEnd", parameterEnd);

        if (parameterEnd < parameterStart) {
            Toast.makeText(this, "El segundo número tiene que ser mayor igual que el primero", Toast.LENGTH_SHORT).show();
        } else {
            initializeList(true);
            FragmentOculto fragment = FragmentOculto.newInstance(parametros);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, fragment, FragmentOculto.TAG);
            ft.commit();
            hiddenFragment = fragment;
        }


    }

    //private ProgressDialog progress;

    @Override
    public void onPreExecute() {
        btnCalculate.setText("CANCELAR");
        //progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progress.show();
    }

    @Override
    public void onProgressUpdate(long primeNumber) {
        resultAdapter.addResultItem(String.valueOf(primeNumber));
        rvResult.smoothScrollToPosition(resultAdapter.getItemCount() - 1);
        //progressMessage = String.format("%.1f%% completado", progreso * 100);
        //progress.setMessage(progressMessage);
    }

    @Override
    public void onPostExecute() {
        btnCalculate.setText("Buscar primos");
        //progress.dismiss();
        resultAdapter.addResultItem("FIN");
        Toast.makeText(this, "FIN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled() {
        if (hiddenFragment != null) {
            Log.d(TAG, "onCancelled isAdded(): " + hiddenFragment.isAdded());
            //initializeList();
            btnCalculate.setText("Buscar primos");
            resultAdapter.addResultItem("CANCELADO");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(hiddenFragment);
            ft.commitAllowingStateLoss();
            //progress.dismiss();
        }
    }

    @Override
    public void inBackground() {
        Log.d(TAG, "inBackground hiddenFragment != null: " + (hiddenFragment != null));
        if (hiddenFragment != null) hiddenFragment.cancelAsyncTask();
    }

}
