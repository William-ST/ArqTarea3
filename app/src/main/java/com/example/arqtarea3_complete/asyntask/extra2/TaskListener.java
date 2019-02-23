package com.example.arqtarea3_complete.asyntask.extra2;

/**
 * Created by William_ST on 18/02/19.
 */

public interface TaskListener {

    void onPreExecute();

    void onProgressUpdate(double progreso);

    void onPostExecute(boolean resultado);

    void onCancelled();

}
