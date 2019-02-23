package com.example.arqtarea3_complete.asyntask.extra4;

/**
 * Created by William_ST on 18/02/19.
 */

public interface TaskListener {

    void onPreExecute();

    void onProgressUpdate(long progreso);

    void onPostExecute();

    void onCancelled();

}
