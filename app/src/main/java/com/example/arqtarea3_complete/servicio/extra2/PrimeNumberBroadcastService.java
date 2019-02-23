package com.example.arqtarea3_complete.servicio.extra2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.arqtarea3_complete.servicio.extra1.ResultCallback;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by William_ST on 23/02/19.
 */

public class PrimeNumberBroadcastService extends Service {

    public static final String PRIMENUMBER_BROADCAST = "PRIMENUMBER_BROADCAST";
    public static final String RESULT = "sha1";

    private final IBinder mBinder = new LocalBinder();
    private final String TAG = "PrimeNumberService";
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 4;
    private static final int MAX_QUEUE_SIZE = 16;

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(MAX_QUEUE_SIZE);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "PrimeNumberService #" + mCount.getAndIncrement());
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }
    };

    private ThreadPoolExecutor mExecutor;

    public class LocalBinder extends Binder {
        PrimeNumberBroadcastService getService() {
            return PrimeNumberBroadcastService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void broadcastResult(Boolean result) {
        Intent intent = new Intent(PRIMENUMBER_BROADCAST);
        intent.putExtra(RESULT, result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    void processPrimeNumber(final long number) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Number " + number + " on Thread " +
                        Thread.currentThread().getName());
                try {
                    // Execute the Long Running Computation
                    final boolean result = isPrimeNumber(number);
                    Log.i(TAG, "¿" + number + " is prime number? : " + result);
                    // Execute the Runnable on UI Thread
                    broadcastResult(result);
                } catch (Exception e) {
                    Log.e(TAG, "Calculating failed", e);
                }
            }
        };
        // Submit the Runnable on the ThreadPool
        mExecutor.execute(runnable);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Starting Calculating Service");
        super.onCreate();
        mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                5, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
        mExecutor.prestartAllCoreThreads();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping Calculating Service");
        super.onDestroy();
        mExecutor.shutdown();
    }

    private boolean isPrimeNumber(long numComprobar) {
        if (numComprobar == 2) return true;
        if (numComprobar < 2 || numComprobar % 2 == 0)
            return false;
        double limite = Math.sqrt(numComprobar) + 0.0001;
        for (long factor = 3; factor < limite; factor += 2) {
            if (numComprobar % factor == 0) return false;
        }
        return true;
    }

}
