package com.example.arqtarea3_complete.servicio.extra1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arqtarea3_complete.R;

public class Ext1MainActivity extends AppCompatActivity implements ResultCallback<Boolean> {

    PrimeNumberService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_ext1_activity_main);
        Button queryButton = findViewById(R.id.btn_has_number_prime);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNumber = findViewById(R.id.et_number);
                if (mService != null) {
                    long number = Long.parseLong(etNumber.getText().toString());
                    mService.processPrimeNumber(number, Ext1MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onResult(Boolean data) {
        TextView et = findViewById(R.id.number_result);
        if (data != null) {
            et.setText(data ? "Número primo" : "Número no primo");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PrimeNumberService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            PrimeNumberService.LocalBinder binder =
                    (PrimeNumberService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            mService = null;
        }
    };
}
