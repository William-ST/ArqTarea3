package com.example.arqtarea3_complete.servicio.exercise3;

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

public class Exe3MainActivity extends AppCompatActivity implements ResultCallback<String> {

    Sha1HashService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_exe3_activity_main);
        Button queryButton = (Button) findViewById(R.id.hashIt);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.text);
                if (mService != null) {
                    mService.getSha1Digest(et.getText().toString(), Exe3MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onResult(String data) {
        TextView et = (TextView) findViewById(R.id.hashResult);
        et.setText(data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, Sha1HashService.class);
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
            Sha1HashService.LocalBinder binder =
                    (Sha1HashService.LocalBinder) service;
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
