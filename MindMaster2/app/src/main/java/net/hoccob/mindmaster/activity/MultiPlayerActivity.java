package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import net.hoccob.mindmaster.BuildConfig;
import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.Game;
import net.hoccob.mindmaster.MultiPlayerStartSwipeListener2;
import net.hoccob.mindmaster.StartSwipeListener2;
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


    private static final int THREAD_ID = 10000;

    MultiPlayerView multiPlayerView;
    LoadingView loadingView;
    Player player;
    Waitlist waitlist;
    Game game;
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
    long answerTime;
    JSONObject jsonOpponent;
    String opponentNickname;

    private GestureDetectorCompat gestureDetectorCompat = null;

    SharedPreferences sharedPrefColor;
    int colorCode;

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

        TrafficStats.setThreadStatsTag(THREAD_ID);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.enableDefaults();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        sharedPrefColor = getSharedPreferences("ColorCode",
                Context.MODE_PRIVATE);
        colorCode = sharedPrefColor.getInt("code", 0);

        Typeface tf = Typeface.createFromAsset(getAssets(), "pristina.ttf");
        multiPlayerView = new MultiPlayerView(this, size.x, size.y, colorCode, tf);
        loadingView = new LoadingView(this, size.x, size.y);
        loadingView.setText("LOADOING!!");

        waitlist = new Waitlist();
        equations = new ArrayList<>();

        player = getIntent().getParcelableExtra("player");
        game = getIntent().getParcelableExtra("game");
        for(int i = 0; i < 12; i++){
            equations.add(getIntent().<Equation>getParcelableArrayListExtra("level" + i));
        }

        ScreenX = size.x;
        ScreenY = size.y;

        jsonOpponent = new JSONObject();


        // Create a common gesture listener object.
        MultiPlayerStartSwipeListener2 gestureListener = new MultiPlayerStartSwipeListener2();

        // Set activity in the listener.
        gestureListener.setActivity(this);

        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);


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

    public void swipeLeft(){
        multiPlayerView.increaseColor();
    }

    public void swipeRight(){
        multiPlayerView.decreaseColor();
    }

    private void checkTime(){
        checkGameTime = new TimerTask() {
            public void run() {
                gameTimeHandler.post(new Runnable(){
                    public void run(){
                        if(multiPlayerView.getGameOver() && !gameOver){
                            SaveScore();
                            new SendAnswer().execute(equations.get(level-1).get(0).getId(), player.getId(), game.getId(), answer, Math.round(System.currentTimeMillis() - answerTime), score, multiPlayerView.getTimer());
                            new SendFinalScore().execute(game.getId(), player.getId(), score);
                            gameOver = true;
                            getOpponentScoreTimer.cancel();
                            gameTimeTimer.cancel();
                            try {
                                setOpponent(new GetOpponentScore().execute(game.getId(), player.getId()).get());
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
        if(output == "0"){
         multiPlayerView.setOpponentScore(0);
        }else {
            try {
                jsonOpponent = new JSONObject(output);
                multiPlayerView.setOpponentScore(jsonOpponent.getInt("score"));
                multiPlayerView.setOpponentTimer((long) jsonOpponent.getInt("timer"));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
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
                            setOpponent(new GetOpponentScore().execute(game.getId(), player.getId()).get());
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
            score = score + 10 * level;
            progress++;
            multiPlayerView.timerPlus(1000);
        }else{
            score = score - 5;
            progress = progress - 2;
            multiPlayerView.timerMinus(1500);
        }
        new SendAnswer().execute(equations.get(level-1).get(0).getId(), player.getId(), game.getId(), answer, Math.round(System.currentTimeMillis() - answerTime), score, multiPlayerView.getTimer());
        answerTime = System.currentTimeMillis();
        multiPlayerView.setScore(score);
        answer = 0;
        multiPlayerView.setAnswer(0);

        // Kaotame progressi 2ra !!11one


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
        multiPlayerView.setOpponentNickname(game.getOpponentNickname());
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

        gestureDetectorCompat.onTouchEvent(motionEvent);

        //if(!multiPlayerView.getGameOver()) {
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    if(answer < 10000000) {
                        if (multiPlayerView.rect_7.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 7;
                            multiPlayerView.setButton_7Clicked(true);
                        }
                        else if (multiPlayerView.rect_8.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 8;
                            multiPlayerView.setButton_8Clicked(true);
                        }
                        else if (multiPlayerView.rect_9.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 9;
                            multiPlayerView.setButton_9Clicked(true);
                        }
                        else if (multiPlayerView.rect_4.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 4;
                            multiPlayerView.setButton_4Clicked(true);
                        }
                        else if (multiPlayerView.rect_5.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 5;
                            multiPlayerView.setButton_5Clicked(true);
                        }
                        else if (multiPlayerView.rect_6.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 6;
                            multiPlayerView.setButton_6Clicked(true);
                        }
                        else if (multiPlayerView.rect_1.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 1;
                            multiPlayerView.setButton_1Clicked(true);
                        }
                        else if (multiPlayerView.rect_2.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 2;
                            multiPlayerView.setButton_2Clicked(true);
                        }
                        else if (multiPlayerView.rect_3.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10 + 3;
                            multiPlayerView.setButton_3Clicked(true);
                        }
                        else if (multiPlayerView.rect_0.contains(motionEvent.getX(), motionEvent.getY())){
                            answer = answer * 10;
                            multiPlayerView.setButton_0Clicked(true);
                        }
                        /*else if (multiPlayerView.button_c.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                            answer = 0;
                        }
                        else if (multiPlayerView.button_backspace.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                            answer = (answer - answer % 10) / 10;
                        }*/

                    }
                   /* if (multiPlayerView.button_backspace.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                        answer = (answer - answer % 10) / 10;
                    } else if (multiPlayerView.button_ent.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                        checkAnswer();
                    }*/

                    multiPlayerView.setAnswer(answer);
                    multiPlayerView.invalidate();

                    break;
                case MotionEvent.ACTION_UP:

                    multiPlayerView.setButton_1Clicked(false);
                    multiPlayerView.setButton_4Clicked(false);
                    multiPlayerView.setButton_7Clicked(false);
                    multiPlayerView.setButton_2Clicked(false);
                    multiPlayerView.setButton_3Clicked(false);
                    multiPlayerView.setButton_5Clicked(false);
                    multiPlayerView.setButton_6Clicked(false);
                    multiPlayerView.setButton_8Clicked(false);
                    multiPlayerView.setButton_9Clicked(false);
                    multiPlayerView.setButton_0Clicked(false);

                    //singlePlayerView.setSettingsClicked(false);
                    multiPlayerView.invalidate();
                    break;
            }
        //}
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
