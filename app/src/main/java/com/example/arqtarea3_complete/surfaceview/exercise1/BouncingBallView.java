package com.example.arqtarea3_complete.surfaceview.exercise1;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by William_ST on 22/02/19.
 */

public class BouncingBallView extends SurfaceView implements SurfaceHolder.Callback {

    private BouncingBallThread bbThread = null;

    public BouncingBallView(Context context) {
        super(context);
        if (bbThread != null) return;
        bbThread = new BouncingBallThread(this);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        bbThread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bbThread.setRunning(true);
        bbThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean reintentar = true;
        bbThread.setRunning(false);
        while (reintentar) {
            try {
                bbThread.join();
                reintentar = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
