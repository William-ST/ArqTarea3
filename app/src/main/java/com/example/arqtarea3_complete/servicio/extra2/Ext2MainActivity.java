package com.example.arqtarea3_complete.servicio.extra2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arqtarea3_complete.R;

public class Ext2MainActivity extends AppCompatActivity {

    PrimeNumberBroadcastService mService;
    boolean mBound = false;

    private DigestReceiver mReceiver = new DigestReceiver();

    private static class DigestReceiver extends BroadcastReceiver {

        private TextView view;

        @Override
        public void onReceive(Context context, Intent intent) {

            if (view != null) {
                boolean result = intent.getBooleanExtra(PrimeNumberBroadcastService.RESULT, false);
                view.setText(result ? "Número primo" : "Númeor no primo");
            } else {
                Log.i("PrimeNumberBroadcast", " ignoring - we're detached");
            }
        }

        public void attach(TextView view) {
            this.view = view;
        }

        public void detach() {
            this.view = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_ext1_activity_main);
        Button queryButton = findViewById(R.id.btn_has_number_prime);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = findViewById(R.id.et_number);
                if (mService != null) {
                    long number = Long.parseLong(et.getText().toString());
                    mService.processPrimeNumber(number);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, PrimeNumberBroadcastService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mReceiver.attach((TextView)
                findViewById(R.id.number_result));
        IntentFilter filter = new IntentFilter(PrimeNumberBroadcastService.PRIMENUMBER_BROADCAST);
        LocalBroadcastManager.getInstance(this).
                registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        LocalBroadcastManager.getInstance(this).
                unregisterReceiver(mReceiver);
        mReceiver.detach();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            PrimeNumberBroadcastService.LocalBinder binder = (PrimeNumberBroadcastService.LocalBinder) service;
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