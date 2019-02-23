package com.example.arqtarea3_complete.surfaceview.exercise1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Boucing1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BouncingBallView(this));
    }
}
