package com.example.arqtarea3_complete;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arqtarea3_complete.R;
import com.example.arqtarea3_complete.asyntask.CancelPrimeNumberAsyntaskActivity;
import com.example.arqtarea3_complete.asyntask.CancelPrimeNumberInbackgroundAsyntaskActivity;
import com.example.arqtarea3_complete.asyntask.HandlerStatusAsyntaskActivity;
import com.example.arqtarea3_complete.asyntask.IndependentAsyntaskClassAsyntaskActivity;
import com.example.arqtarea3_complete.asyntask.LockChangeOrientationActivity;
import com.example.arqtarea3_complete.asyntask.PrimeNumberAsyntaskActivity;
import com.example.arqtarea3_complete.asyntask.SaveStatusHiddenFragmentActivity;
import com.example.arqtarea3_complete.asyntask.extra1.CompleteSaveStatusHiddenActitivty;
import com.example.arqtarea3_complete.asyntask.extra2.ProgressbarActivity;
import com.example.arqtarea3_complete.asyntask.extra4.IntervalSearchPrimeNumberActivity;
import com.example.arqtarea3_complete.asyntask.extra5.ConcurrentPrimeNumberActivity;
import com.example.arqtarea3_complete.servicio.exercise1.Exe1MainActivity;
import com.example.arqtarea3_complete.servicio.exercise2.Exe2MainActivity;
import com.example.arqtarea3_complete.servicio.exercise3.Exe3MainActivity;
import com.example.arqtarea3_complete.servicio.exercise4.Exe4MainActivity;
import com.example.arqtarea3_complete.surfaceview.exercise1.Boucing1Activity;
import com.example.arqtarea3_complete.surfaceview.exercise2.Boucing2Activity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.exercise_1) {
            startActivity(new Intent(this, PrimeNumberAsyntaskActivity.class));
        } else if (id == R.id.exercise_2) {
            startActivity(new Intent(this, CancelPrimeNumberAsyntaskActivity.class));
        } else if (id == R.id.exercise_3) {
            startActivity(new Intent(this, CancelPrimeNumberInbackgroundAsyntaskActivity.class));
        } else if (id == R.id.exercise_4) {
            startActivity(new Intent(this, HandlerStatusAsyntaskActivity.class));
        } else if (id == R.id.exercise_5) {
            startActivity(new Intent(this, IndependentAsyntaskClassAsyntaskActivity.class));
        } else if (id == R.id.exercise_6) {
            startActivity(new Intent(this, LockChangeOrientationActivity.class));
        } else if (id == R.id.exercise_7) {
            startActivity(new Intent(this, SaveStatusHiddenFragmentActivity.class));
        } else if (id == R.id.exercise_8) {
            startActivity(new Intent(this, CompleteSaveStatusHiddenActitivty.class));
        } else if (id == R.id.exercise_9) {
            startActivity(new Intent(this, ProgressbarActivity.class));
        } else if (id == R.id.exercise_10) {
            startActivity(new Intent(this, IntervalSearchPrimeNumberActivity.class));
        } else if (id == R.id.exercise_11) {
            startActivity(new Intent(this, ConcurrentPrimeNumberActivity.class));
        } else if (id == R.id.exercise_21) {
            startActivity(new Intent(this, Exe1MainActivity.class));
        } else if (id == R.id.exercise_22) {
            startActivity(new Intent(this, Exe2MainActivity.class));
        } else if (id == R.id.exercise_23) {
            startActivity(new Intent(this, Exe3MainActivity.class));
        } else if (id == R.id.exercise_24) {
            startActivity(new Intent(this, Exe4MainActivity.class));
        } else if (id == R.id.exercise_31) {
            startActivity(new Intent(this, Boucing1Activity.class));
        } else if (id == R.id.exercise_32) {
            startActivity(new Intent(this, Boucing2Activity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
