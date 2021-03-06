package net.hoccob.mindmaster.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import net.hoccob.mindmaster.CreateEquation;
import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.StartSwipeListener2;
import net.hoccob.mindmaster.view.SinglePlayerView;

import java.util.Timer;
import java.util.TimerTask;


public class SinglePlayerActivity extends Activity {

	private static int currentAnswer;
	SinglePlayerView singlePlayerView;
	String answer;
	private int colorCode = 0;
	private GestureDetectorCompat gestureDetectorCompat = null;
	private Equation equation;
	private CreateEquation createEquation;
	private int gameMode;
	private int level = 1;
	private int score;
	private boolean gameOver;
	//private long gameTimeTimer;
	private long endTime = 0;
	private TimerTask gameTimer;
	private long timeLeft;
	Handler gameTimeHandler = new Handler();
	Timer gameTimeTimer;
	private int wrongAnswers = 0;
	private int correctAnswers = 0;



	SharedPreferences sharedPrefColor;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		Typeface tf = Typeface.createFromAsset(getAssets(), "pristina.ttf");
		singlePlayerView = new SinglePlayerView(this, size.x, size.y, tf);
		endTime = System.currentTimeMillis() + 50000;
		sharedPrefColor = getSharedPreferences("ColorCode",
				Context.MODE_PRIVATE);
		colorCode = sharedPrefColor.getInt("code", 0);

		// Create a common gesture listener object.
		StartSwipeListener2 gestureListener = new StartSwipeListener2();

		// Set activity in the listener.
		gestureListener.setActivity(this);

		// Create the gesture detector with the gesture listener.
		gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

		int gameMode = 1;
		int level = 0;

		createEquation = new CreateEquation(gameMode);
		equation = createEquation.getEquation(0);
		singlePlayerView.setCurrentEquation(equation.getStrOperation());

	}

	public void swipeLeft(){
		singlePlayerView.increaseColor();
	}

	public void swipeRight(){
		singlePlayerView.decreaseColor();
	}

	@Override
	protected void onStart() {
		super.onStart();
		setContentView(singlePlayerView);
		//startGameTimeTimer();
		timerHandler.postDelayed(timerRunnable, 0);


	}

	@Override
	protected void onResume() {
		super.onResume();
		//singlePlayerView.draw();
		//singlePlayerView.startGame();
		setContentView(singlePlayerView);
		hideSystemUI();
	}

	@Override
	protected void onPause(){
		super.onPause();
		//singlePlayerView.pause();
	}

	@Override
	protected void onDestroy(){
		SharedPreferences.Editor colorEditor;
		colorEditor = sharedPrefColor.edit();

		colorEditor.putInt("code",colorCode);
		colorEditor.apply();
		super.onDestroy();

       /* int x;
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
        }*/
	}

	public void checkAnswer(){

		if(equation.getAnswer() == currentAnswer && correctAnswers == 5){
			score += 50 * level;
			level = level + 1;
			singlePlayerView.setCurrentScoreText(Integer.toString(score));
			endTime += 3000;
			singlePlayerView.addToCurrentTime();
			//singlePlayerView.invalidate();
			correctAnswers = 0;
			wrongAnswers = 0;
		}

		else if (equation.getAnswer() == currentAnswer){
			score += 50 * level;
			singlePlayerView.setCurrentScoreText(Integer.toString(score));
			endTime += 2000;
			singlePlayerView.addToCurrentTime();
			correctAnswers++;
		}
		else if (equation.getAnswer() != currentAnswer && wrongAnswers == 2){
			if (level > 1) {level = level - 1;}
			score -= 25 * level;
			singlePlayerView.setCurrentScoreText(Integer.toString(score));
			wrongAnswers = 0;
			endTime -= 2000;
			singlePlayerView.subtractFromCurrentTime();
		}
		else if (equation.getAnswer() != currentAnswer){
			score -= level * 25;
			singlePlayerView.setCurrentScoreText(Integer.toString(score));
			wrongAnswers++;
			endTime -= 2000;
			singlePlayerView.subtractFromCurrentTime();
		}


		equation = createEquation.getEquation(level);
		singlePlayerView.setCurrentEquation(equation.getStrOperation());
		currentAnswer = 0;
		singlePlayerView.setAnswerText("");
		//singlePlayerView.setAnswer(0);
	}
	//private void startGameTimeTimer(){
		//gameTimeTimer = new Timer();

	//	gameTimeTimer = endTime - System.currentTimeMillis();

	//}
	/*public void startGame() {
		new CountDownTimer(50000, 1000) {

			public void onTick(long millisUntilFinished) {
				//mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
				singlePlayerView.setCurrentTimeText(millisUntilFinished);
				singlePlayerView.setCurrentTime(millisUntilFinished);
				singlePlayerView.invalidate();
			}

			public void onFinish() {
				singlePlayerView.setCurrentTimeText(0);
				singlePlayerView.invalidate();
			}
		}.start();

	}*/
	/*public void startGame2() {

		timeLeft = endTime - System.currentTimeMillis();
		gameTimer = new TimerTask() {
			public void run() {
				gameTimeHandler.post(new Runnable() {
					public void run() {
						//singlePlayerView.setCurrentTime(timeLeft);
						//singlePlayerView.invalidate();

							singlePlayerView.setCurrentTimeText(timeLeft);
							singlePlayerView.setCurrentTime(timeLeft);
							singlePlayerView.invalidate();

					}

				});
			}
		};
	}*/


	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {

		@Override
		public void run() {

			timeLeft = endTime - System.currentTimeMillis();

			singlePlayerView.setCurrentTimeText(timeLeft);
			singlePlayerView.setCurrentTime(timeLeft);
			singlePlayerView.invalidate();
			timerHandler.post(this);
			//timerHandler.postDelayed(this, 1000);
		}
	};

	/*private void startGameTimeTimer(){
		gameTimeTimer = new Timer();
		startGame2();

		gameTimeTimer.schedule(gameTimer, 0, 1);
	}*/


	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {

		gestureDetectorCompat.onTouchEvent(motionEvent);

		switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:

				if(singlePlayerView.rect_1.contains(motionEvent.getX(),motionEvent.getY()))
				{
					singlePlayerView.setButton_1Clicked(true);
					calculateAnswer(1);
				}else if (singlePlayerView.rect_4.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_4Clicked(true);
					calculateAnswer(4);
				}
				else if(singlePlayerView.rect_7.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_7Clicked(true);
					calculateAnswer(7);
				}
				else if(singlePlayerView.rect_2.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_2Clicked(true);
					calculateAnswer(2);
				}
				else if(singlePlayerView.rect_3.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_3Clicked(true);
					calculateAnswer(3);
				}
				else if(singlePlayerView.rect_5.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_5Clicked(true);
					calculateAnswer(5);
				}
				else if(singlePlayerView.rect_6.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_6Clicked(true);
					calculateAnswer(6);
				}
				else if(singlePlayerView.rect_8.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_8Clicked(true);
					calculateAnswer(8);
				}
				else if(singlePlayerView.rect_9.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_9Clicked(true);
					calculateAnswer(9);
				}
				else if(singlePlayerView.rect_0.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_0Clicked(true);
					calculateAnswer(0);
				}
				else if(singlePlayerView.rect_backspace.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_BackspaceClicked(true);
					calculateAnswer(11);
				}
				else if(singlePlayerView.rect_c.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_cClicked(true);
					calculateAnswer(12);
				}
				else if (singlePlayerView.rect_enter.contains(motionEvent.getX(), motionEvent.getY()))
				{
					singlePlayerView.setButton_EnterClicked(true);
					checkAnswer();
				}
                /*
                else if(mainView.settingsRect.contains(motionEvent.getX(),motionEvent.getY()))
                {
                    mainView.setSettingsClicked(true);
                }*/
                //singlePlayerView.setAnswer(currentAnswer);
				singlePlayerView.setAnswerText(String.valueOf(currentAnswer));
				//singlePlayerView.setCurrentTimeText(getTime());
				//singlePlayerView.invalidate();
				break;

			case MotionEvent.ACTION_UP:

				singlePlayerView.setButton_1Clicked(false);
				singlePlayerView.setButton_4Clicked(false);
				singlePlayerView.setButton_7Clicked(false);
				singlePlayerView.setButton_2Clicked(false);
				singlePlayerView.setButton_3Clicked(false);
				singlePlayerView.setButton_5Clicked(false);
				singlePlayerView.setButton_6Clicked(false);
				singlePlayerView.setButton_8Clicked(false);
				singlePlayerView.setButton_9Clicked(false);
				singlePlayerView.setButton_0Clicked(false);
				singlePlayerView.setButton_BackspaceClicked(false);
				singlePlayerView.setButton_cClicked(false);
				singlePlayerView.setButton_EnterClicked(false);

				//singlePlayerView.setSettingsClicked(false);
				singlePlayerView.invalidate();

              /*  if(mainView.startRect.contains(motionEvent.getX(),motionEvent.getY()))
                {
                    if(player.getId() > 0 && player.getNickname() != null) {
                        //Intent intent4 = new Intent(this, LoadingActivity.class);
                        System.gc();
                        intent4.putExtra("player", player);
                        startActivity(intent4);
                    }else{
                        Toast.makeText(this, "Not logged in!", Toast.LENGTH_LONG).show();
                    }
                }else if(mainView.practiceRect.contains(motionEvent.getX(), motionEvent.getY()))
                {
                    Intent intent = new Intent(this, SinglePlayerActivity.class);
                    startActivity(intent);
               */
				break;

		}
		return true;
	}


	public void calculateAnswer(int input)
	{
		if (input < 10){
			currentAnswer = currentAnswer * 10 + input;
		}
		else if (input == 11){
			currentAnswer = currentAnswer / 10;
		}
		else if (input == 12){
			currentAnswer = 0;
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
