package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;

import net.hoccob.mindmaster.view.SinglePlayerView;


public class SinglePlayerActivity extends Activity {

    SinglePlayerView singlePlayerView;
    String answer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        singlePlayerView = new SinglePlayerView(this, size.x, size.y);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(singlePlayerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //singlePlayerView.draw();
        singlePlayerView.startGame();
        setContentView(singlePlayerView);
        hideSystemUI();
    }

    @Override
    protected void onPause(){
        super.onPause();
        singlePlayerView.pause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        int x;
        int y;
        int z = singlePlayerView.getScore();


        if (singlePlayerView.getGameOver()) {
            SharedPreferences sharedPref = getSharedPreferences(
                    "HighScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if (sharedPref.contains("1")) {
                if(sharedPref.getInt("5", 0) < z){
                    if(sharedPref.getInt("4", 0) < z){
                        if(sharedPref.getInt("3", 0) < z){
                            if(sharedPref.getInt("2", 0) < z){
                                if(sharedPref.getInt("1", 0) < z){
                                    x = sharedPref.getInt("1", 0);
                                    editor.putInt("1", z);
                                    y = sharedPref.getInt("2", 0);
                                    editor.putInt("2", x);
                                    x = sharedPref.getInt("3", 0);
                                    editor.putInt("3", y);
                                    y = sharedPref.getInt("4", y);
                                    editor.putInt("4", x);
                                    editor.putInt("5", y);

                                }
                                else {
                                    x = sharedPref.getInt("2", 0);
                                    editor.putInt("2", singlePlayerView.getScore());
                                    y = sharedPref.getInt("3", 0);
                                    editor.putInt("3", x);
                                    x = sharedPref.getInt("4", 0);
                                    editor.putInt("4", y);
                                    editor.putInt("5", x);
                                }
                            }
                            else {
                                x = sharedPref.getInt("3", 0);
                                editor.putInt("3", singlePlayerView.getScore());
                                y = sharedPref.getInt("4", 0);
                                editor.putInt("4", x);
                                editor.putInt("5", y);
                            }
                        }
                        else {
                            x = sharedPref.getInt("4", 0);
                            editor.putInt("4", singlePlayerView.getScore());
                            editor.putInt("5", x);
                        }
                    }else{
                    editor.putInt("5", singlePlayerView.getScore());}

                }
            }


                else{
                    if (singlePlayerView.getScore() > 0){
                        editor.putInt("1", singlePlayerView.getScore());}
                    else {
                        editor.putInt("1", 0);
                    }
                        editor.putInt("2", 0);
                        editor.putInt("3", 0);
                        editor.putInt("4", 0);
                        editor.putInt("5", 0);


                    }
            editor.commit();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
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
