package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.server.GetOpponentScore;
import net.hoccob.mindmaster.server.SendAnswer;
import net.hoccob.mindmaster.server.SendFinalScore;
import net.hoccob.mindmaster.view.LoadingView;
import net.hoccob.mindmaster.view.MultiPlayerView;
import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.Waitlist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;


public class MultiPlayerActivity extends Activity {

    MultiPlayerView multiPlayerView;
    LoadingView loadingView;
    Player player;
    Waitlist waitlist;
    ArrayList<ArrayList<Equation>> equations;
    Handler gameTimeHandler = new Handler();
    Timer gameTimeTimer;
    TimerTask checkGameTime;
    Handler getOpponentScoreHandler = new Handler();
    Timer getOpponentScoreTimer;
    TimerTask checkOpponentScore;
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
    JSONObject jsonOpponent;

    private void SaveScore(){

        int x;
        int y;
        SharedPreferences sharedPref = getSharedPreferences(
                "HighScoreMultiplayer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.contains("1")) {
            if(sharedPref.getInt("5", 0) < score){
                if(sharedPref.getInt("4", 0) < score){
                    if(sharedPref.getInt("3", 0) < score){
                        if(sharedPref.getInt("2", 0) < score){
                            if(sharedPref.getInt("1", 0) < score){
                                x = sharedPref.getInt("1", 0);
                                editor.putInt("1", score);
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
                                editor.putInt("2", score);
                                y = sharedPref.getInt("3", 0);
                                editor.putInt("3", x);
                                x = sharedPref.getInt("4", 0);
                                editor.putInt("4", y);
                                editor.putInt("5", x);
                            }
                        }
                        else {
                            x = sharedPref.getInt("3", 0);
                            editor.putInt("3", score);
                            y = sharedPref.getInt("4", 0);
                            editor.putInt("4", x);
                            editor.putInt("5", y);
                        }
                    }
                    else {
                        x = sharedPref.getInt("4", 0);
                        editor.putInt("4", score);
                        editor.putInt("5", x);
                    }
                }else{
                    editor.putInt("5", score);}

            }
        }


        else{
            if (score > 0){
                editor.putInt("1", score);}
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

        jsonOpponent = new JSONObject();


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
        multiPlayerView.setPlay(false);
        finish();
    }

    private void startGameTimeTimer(){
        gameTimeTimer = new Timer();
        checkTime();

        gameTimeTimer.schedule(checkGameTime, 0, 500);
    }

    private void checkTime(){
        checkGameTime = new TimerTask() {
            public void run() {
                gameTimeHandler.post(new Runnable(){
                    public void run(){
                        if(multiPlayerView.getGameOver() && !gameOver){
                            SaveScore();
                            new SendFinalScore().execute(gameId, player.getId(), score);
                            gameOver = true;
                            getOpponentScoreTimer.cancel();
                            try {
                                setOpponent(new GetOpponentScore().execute(gameId, player.getId()).get());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
    }

    private void setOpponent(String output){
        try {
            jsonOpponent = new JSONObject(output);
            multiPlayerView.setOpponentScore(jsonOpponent.getInt("score"));
            multiPlayerView.setOpponentTimer((long) jsonOpponent.getInt("timer"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void startGetOpponentScoreTimer(){
        getOpponentScoreTimer = new Timer();
        checkScore();

        getOpponentScoreTimer.schedule(checkOpponentScore, 0, 1000);
    }

    private void checkScore(){
        checkOpponentScore = new TimerTask() {
            public void run(){
                getOpponentScoreHandler.post(new Runnable(){
                    public void run() {
                        try {
                            setOpponent(new GetOpponentScore().execute(gameId, player.getId()).get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
    }

    public void newEquation(){
        currentOperation = equations.get(level-1).get(0).getStrOperation();
        correctAnswer = equations.get(level-1).get(0).getAnswer();

        equations.get(level-1).remove(0);
        multiPlayerView.setCurrentOperation(currentOperation);
    }

    public void checkAnswer(){

        if(answer == correctAnswer){
            score = score + 10;
            progress++;
            multiPlayerView.timerPlus(1000);
        }else{
            score = score - 5;
            progress = progress - 2;
            multiPlayerView.timerMinus(1500);
        }
        new SendAnswer().execute(equations.get(level-1).get(0).getId(), player.getId(), gameId, answer, Math.round(System.currentTimeMillis() - answerTime), score);
        answerTime = System.currentTimeMillis();
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
        startGameTimeTimer();
        startGetOpponentScoreTimer();
    }

    @Override
    protected void onDestroy(){

        multiPlayerView.setPlay(false);
        super.onDestroy();
        finish();
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
                    if(answer < 10000000) {
                        if (motionEvent.getX() > 0 && motionEvent.getX() < ScreenX / 3 && motionEvent.getY() > (ScreenY / 7) * 3 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 4 + (ScreenY / 64)) {
                            answer = answer * 10 + 7;
                        } else if (motionEvent.getX() > ScreenX / 3 && motionEvent.getX() < ScreenX / 1.5 && motionEvent.getY() > (ScreenY / 7) * 3 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 4 + (ScreenY / 64)) {
                            answer = answer * 10 + 8;
                        } else if (motionEvent.getX() > ScreenX / 1.5 && motionEvent.getX() < ScreenX && motionEvent.getY() > (ScreenY / 7) * 3 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 4 + (ScreenY / 64)) {
                            answer = answer * 10 + 9;
                        } else if (motionEvent.getX() > 0 && motionEvent.getX() < ScreenX / 3 && motionEvent.getY() > (ScreenY / 7) * 4 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 5 + (ScreenY / 64)) {
                            answer = answer * 10 + 4;
                        } else if (motionEvent.getX() > ScreenX / 3 && motionEvent.getX() < ScreenX / 1.5 && motionEvent.getY() > (ScreenY / 7) * 4 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 5 + (ScreenY / 64)) {
                            answer = answer * 10 + 5;
                        } else if (motionEvent.getX() > ScreenX / 1.5 && motionEvent.getX() < ScreenX && motionEvent.getY() > (ScreenY / 7) * 4 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 5 + (ScreenY / 64)) {
                            answer = answer * 10 + 6;
                        } else if (motionEvent.getX() > 0 && motionEvent.getX() < ScreenX / 3 && motionEvent.getY() > (ScreenY / 7) * 5 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 6 + (ScreenY / 64)) {
                            answer = answer * 10 + 1;
                        } else if (motionEvent.getX() > ScreenX / 3 && motionEvent.getX() < ScreenX / 1.5 && motionEvent.getY() > (ScreenY / 7) * 5 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 6 + (ScreenY / 64)) {
                            answer = answer * 10 + 2;
                        } else if (motionEvent.getX() > ScreenX / 1.5 && motionEvent.getX() < ScreenX && motionEvent.getY() > (ScreenY / 7) * 5 + (ScreenY / 64) && motionEvent.getY() < (ScreenY / 7) * 6 + (ScreenY / 64)) {
                            answer = answer * 10 + 3;
                        } else if (motionEvent.getX() > ScreenX / 3 && motionEvent.getX() < ScreenX / 1.5 && motionEvent.getY() > (ScreenY / 7) * 6 + (ScreenY / 64) && motionEvent.getY() < ScreenY) {
                            answer = answer * 10;
                        } else if (motionEvent.getX() < ScreenX / 2 && motionEvent.getY() > (ScreenY / 7) * 2 + (ScreenY / 32) && motionEvent.getY() < (ScreenY / 7) * 3 + (ScreenY / 64)) {
                            answer = 0;
                        }
                    }
                    if (motionEvent.getX() > ScreenX / 2 && motionEvent.getY() > (ScreenY / 7) * 2 + (ScreenY / 32) && motionEvent.getY() < (ScreenY / 7) * 3 + (ScreenY / 64)) {
                        answer = (answer - answer % 10) / 10;
                    } else if (motionEvent.getX() > ScreenX / 1.5 && motionEvent.getX() < ScreenX && motionEvent.getY() > (ScreenY / 7) * 6 + (ScreenY / 64) && motionEvent.getY() < ScreenY) {
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
