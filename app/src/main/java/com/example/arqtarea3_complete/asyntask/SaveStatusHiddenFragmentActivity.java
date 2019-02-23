package com.example.arqtarea3_complete.asyntask;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arqtarea3_complete.MainActivity;
import com.example.arqtarea3_complete.R;

public class SaveStatusHiddenFragmentActivity extends AppCompatActivity implements FragmentOculto.TaskListener {

    private static final String TAG = MainActivity.class.getName();
    private EditText inputField, resultField;
    private Button primecheckbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_number_asyntask);
        inputField = findViewById(R.id.inputField);
        resultField = findViewById(R.id.resultField);
        primecheckbutton = findViewById(R.id.primecheckbutton);
    }

    public void triggerPrimecheck(View v) {
        long parameter = Long.parseLong(inputField.getText().toString());
        Bundle parametros = new Bundle();
        parametros.putLong("numComprobar", parameter);
        FragmentOculto fragment = FragmentOculto.newInstance(parametros);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment, FragmentOculto.TAG);
        ft.commit();
    }

    @Override
    public void onPreExecute() {
        resultField.setText("");
        primecheckbutton.setText("CANCELAR");
    }

    @Override
    public void onProgressUpdate(double progreso) {
        resultField.setText(String.format("%.1f%% completado", progreso * 100));
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
