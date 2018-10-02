package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.server.SendAnswer;
import net.hoccob.mindmaster.view.LoadingView;
import net.hoccob.mindmaster.view.MultiPlayerView;
import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.Waitlist;

import java.util.ArrayList;


public class MultiPlayerActivity extends Activity {

    MultiPlayerView multiPlayerView;
    LoadingView loadingView;
    Player player;
    Waitlist waitlist;
    ArrayList<ArrayList<Equation>> equations;
    int ScreenX;
    int ScreenY;
    int answer;
    int progress;
    int level = 1;
    boolean gameOver = false;
    int score;
    String currentOperation = "";
    int correctAnswer = 0;
    int gameId;
    long answerTime;

    public MultiPlayerActivity(){
    }


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

        player = getIntent().getParcelableExtra("player");
        gameId = getIntent().getIntExtra("gameId", 0);
        for(int i = 0; i < 12; i++){
            equations.add(getIntent().<Equation>getParcelableArrayListExtra("level" + i));
        }

        ScreenX = size.x;
        ScreenY = size.y;


    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(multiPlayerView);

        startGame();

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
    }

    public void newEquation(){
        currentOperation = equations.get(level-1).get(0).getStrOperation();
        correctAnswer = equations.get(level-1).get(0).getAnswer();


        equations.get(level-1).remove(0);
        multiPlayerView.setCurrentOperation(currentOperation);
    }

    public void checkAnswer(){

        new SendAnswer().execute(equations.get(level-1).get(0).getId(), player.getId(), gameId, answer, Math.round(System.currentTimeMillis() - answerTime));
        answerTime = System.currentTimeMillis();

        if(answer == correctAnswer){
            score = score + 10;
            progress++;
        }else{
            score = score - 5;
            progress = progress - 2;
        }
        multiPlayerView.setScore(score);
        answer = 0;
        multiPlayerView.setAnswer(0);

        if(progress < 3){
            level = 1;
        }else if(progress < 6){
            level = 2;
        }else if(progress < 9){
            level = 3;
        }else if(progress < 12){
            level = 4;
        }else if(progress < 15){
            level = 5;
        }else if(progress < 18){
            level = 6;
        }else if(progress < 21){
            level = 7;
        }else if(progress < 24){
            level = 8;
        }else if(progress < 27){
            level = 9;
        }else if(progress < 30){
            level = 10;
        }else if(progress < 33){
            level = 11;
        }else{
            level = 12;
        }

        newEquation();
    }

    public void startGame(){
        answer = 0;
        gameOver = false;
        answerTime = System.currentTimeMillis();
        newEquation();
    }

    @Override
    protected void onDestroy(){
        int final_score = multiPlayerView.getScore();

        if (multiPlayerView.getGameOver()){
            SharedPreferences sharedPref = getSharedPreferences(
                    "HighScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Competitive1", final_score);
            editor.commit();
        }
        super.onDestroy();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent motionEvent) {

        if(!multiPlayerView.getGameOver()) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:

                    if (motionEvent.getX() > ScreenX / 40 && motionEvent.getX() < (ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > (ScreenY / 3) + (((ScreenY / 3) * 2) / 45) && motionEvent.getY() < ((ScreenY / 3) + (((ScreenY / 3) * 2) / 45) + (ScreenY / 3 * 2 / 45 * 10))) {
                        answer = answer * 10 + 7;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 4 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2) && motionEvent.getY() > (ScreenY / 3) + (((ScreenY / 3) * 2) / 45) && motionEvent.getY() < ((ScreenY / 3) + (((ScreenY / 3) * 2) / 45) + (ScreenY / 3 * 2 / 45 * 10))) {
                        answer = answer * 10 + 8;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 40 + ScreenX / 2 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2 + ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > (ScreenY / 3) + (((ScreenY / 3) * 2) / 45) && motionEvent.getY() < ((ScreenY / 3) + (((ScreenY / 3) * 2) / 45) + (ScreenY / 3 * 2 / 45 * 10))) {
                        answer = answer * 10 + 9;
                    } else if (motionEvent.getX() > ScreenX / 40 && motionEvent.getX() < (ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) + (ScreenY / 3 * 2 / 45 * 10)) {
                        answer = answer * 10 + 4;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 4 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) + (ScreenY / 3 * 2 / 45 * 10)) {
                        answer = answer * 10 + 5;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 40 + ScreenX / 2 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2 + ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) + (ScreenY / 3 * 2 / 45 * 10)) {
                        answer = answer * 10 + 6;
                    } else if (motionEvent.getX() > ScreenX / 40 && motionEvent.getX() < (ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                        answer = answer * 10 + 1;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 4 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                        answer = answer * 10 + 2;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 40 + ScreenX / 2 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2 + ScreenX / 4 + ScreenX / 40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                        answer = answer * 10 + 3;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 4 && motionEvent.getX() < (ScreenX / 20 + ScreenX / 2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3) && motionEvent.getY() < ((ScreenY / 3) + ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 4))) {
                        answer = answer * 10;
                    } else if (motionEvent.getX() > (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)) && motionEvent.getX() < ScreenX - ScreenX / 40 && motionEvent.getY() > (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45))) && motionEvent.getY() < (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45)) + ScreenY / 3 * 2 / 3)) {
                        answer = 0;
                    } else if (motionEvent.getX() > (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)) && motionEvent.getX() < ScreenX - ScreenX / 40 && motionEvent.getY() > (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45 * 2)) + ScreenY / 3 * 2 / 3) && motionEvent.getY() < (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45 * 2)) + ScreenY / 3 * 2 / 3 * 2)) {
                        answer = (answer - answer % 10) / 10;
                    } else if (motionEvent.getX() > ScreenX / 20 + ScreenX / 4 && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                        checkAnswer();
                    }

                    multiPlayerView.setAnswer(answer);

                    break;
            }
        }
        return true;
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
