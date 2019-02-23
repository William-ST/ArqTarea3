package com.example.arqtarea3_complete.asyntask;

/**
 * Created by William_ST on 23/02/19.
 */

public interface TaskListener {

    void onPreExecute();

    void onProgressUpdate(double progreso);

    void onPostExecute(boolean resultado);

    void onCancelled();

}
