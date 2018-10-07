package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.Game;
import net.hoccob.mindmaster.view.LoadingView;
import net.hoccob.mindmaster.view.MultiPlayerView;
import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.server.RequestGame;
import net.hoccob.mindmaster.Waitlist;

import java.util.ArrayList;


public class LoadingActivity extends Activity {

    MultiPlayerView multiPlayerView;
    LoadingView loadingView;
    Player player;
    Waitlist waitlist;
    ArrayList<ArrayList<Equation>> equations;
    int ScreenX;
    int ScreenY;
    RequestGame requestGame;
    Game game;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.enableDefaults();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        multiPlayerView = new MultiPlayerView(this, size.x, size.y);
        loadingView = new LoadingView(this, size.x, size.y);
        loadingView.setText("LOADOING!!");

        waitlist = new Waitlist();
        equations = new ArrayList<>();
        game = new Game();

        ScreenX = size.x;
        ScreenY = size.y;


    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(loadingView);

        player = getIntent().getParcelableExtra("player");

        final Intent intent = new Intent(this, MultiPlayerActivity.class);

        requestGame = new RequestGame(player, waitlist, equations, game, new RequestGame.AsyncResponse(){

            @Override
            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                //System.out.println("Joudsin siia");
                intent.putExtra("player", player);
                //intent.putExtra("gameId", waitlist.getGameId());
                intent.putExtra("game", game);
                for(int i = 0; i < 12; i++) {
                    intent.putParcelableArrayListExtra("level" + i, equations.get(i));
                }
                startActivity(intent);
                finish();
            }
        });
        requestGame.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setContentView(loadingView);
        hideSystemUI();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //multiPlayerView.pause();
        requestGame.cancel(true);
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        requestGame.cancel(true);
        finish();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }else{
            requestGame.cancel(true);
            finish();
        }
    }


    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
