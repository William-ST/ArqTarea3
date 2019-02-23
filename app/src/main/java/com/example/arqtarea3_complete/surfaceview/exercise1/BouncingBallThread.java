package com.example.arqtarea3_complete.surfaceview.exercise1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.arqtarea3_complete.R;

/**
 * Created by William_ST on 22/02/19.
 */

public class BouncingBallThread extends Thread {
    static final long FPS = 10;
    private SurfaceView superfView;
    private int width, height;
    private boolean running = false;
    private int pos_x = -1;
    private int pos_y = -1;
    private int xVelocidad = 10;
    private int yVelocidad = 5;
    private BitmapDrawable pelota;

    public BouncingBallThread(SurfaceView view) {
        this.superfView = view;
        // Coloca una imagen de tu elección
        pelota = (BitmapDrawable) view.getContext().
                getResources().getDrawable(R.drawable.pelota);
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas canvas = null;
            startTime = System.currentTimeMillis();
            try {
                // Bloqueamos el canvas de la superficie para dibujarlo
                canvas = superfView.getHolder().lockCanvas();
                // Sincronizamos el método draw() de la superficie para
                // que se ejecute como un bloque
                synchronized (superfView.getHolder()) {
                    if (canvas != null) doDraw(canvas);
                }
            } finally {
                // Liberamos el canvas de la superficie desbloqueándolo
                if (canvas != null) {
                    superfView.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            // Tiempo que debemos parar la ejecución del hilo
            sleepTime = ticksPS - System.currentTimeMillis() - startTime;
            // Paramos la ejecución del hilo
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {
            }
        }
    }

    // Evento que se lanza cada vez que es necesario dibujar la superficie
    protected void doDraw(Canvas canvas) {
        if (pos_x < 0 && pos_y < 0) {
            pos_x = this.width / 2;
            pos_y = this.height / 2;
        } else {
            pos_x += xVelocidad;
            pos_y += yVelocidad;
            if ((pos_x > this.width - pelota.getBitmap().getWidth()) ||
                    (pos_x < 0)) {
                xVelocidad = xVelocidad * -1;
            }
            if ((pos_y > this.height - pelota.getBitmap().getHeight()) ||
                    (pos_y < 0)) {
                yVelocidad = yVelocidad * -1;
            }
        }
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(pelota.getBitmap(), pos_x, pos_y, null);
    }

    // Se usa para establecer el nuevo tamaño de la superficie
    public void setSurfaceSize(int width, int height) {
        // Sincronizamos superficie para que ningún proceso pueda acceder
        synchronized (superfView) {
            this.width = width;
            this.height = height;
        }
    }
}