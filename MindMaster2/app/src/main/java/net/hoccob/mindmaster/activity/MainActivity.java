package net.hoccob.mindmaster.activity;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.hoccob.mindmaster.ASyncTasks.ChangeView;
import net.hoccob.mindmaster.Button2;
import net.hoccob.mindmaster.ChangeColor;
import net.hoccob.mindmaster.DetectSwipeGestureListener;
import net.hoccob.mindmaster.Player;
//import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.server.LogIn;
import net.hoccob.mindmaster.server.SendNickname;
import net.hoccob.mindmaster.view.MainView;
import net.hoccob.mindmaster.view.StartView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final int THREAD_ID = 10000;

    public int y;
    public int x;
    StartView mainView;
    Player player;

    private int colorCode = 0;

    private ArrayList<Button2> startButtons = new ArrayList<>();
    private ArrayList<Button2> practiceButtons = new ArrayList<>();
    private ArrayList<Button2> highScoreButtons = new ArrayList<>();
    private ArrayList<Button2> settingsButtons = new ArrayList<>();

    SharedPreferences sharedPrefColor;

    Intent intent4;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1;


    private GestureDetectorCompat gestureDetectorCompat = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TrafficStats.setThreadStatsTag(THREAD_ID);
        player = new Player();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SharedPreferences sharedPrefStart = getSharedPreferences("AccountName",
                Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("Nickname",
                Context.MODE_PRIVATE);
        String Acc = sharedPrefStart.getString("acc", null);
        if (Acc == null) {
                Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
                startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
        }
        if (sharedPref.getString("nickname", null) == null){
            pickNickname();
        }
        else{
            player.setNickname(sharedPref.getString("nickname", null));
        }

        sharedPrefColor = getSharedPreferences("ColorCode",
                Context.MODE_PRIVATE);
        colorCode = sharedPrefColor.getInt("code", 0);

        Typeface tf = Typeface.createFromAsset(getAssets(), "pristina.ttf");

        mainView = new StartView(this, size.x, size.y, tf);
        y = size.y;
        x = size.x;


        intent4 = new Intent(this, LoadingActivity.class);

        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                System.out.println(data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
                SharedPreferences sharedPrefStart = getSharedPreferences("AccountName",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefStart.edit();
                editor.putString("acc", data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                editor.commit();
                System.out.println(sharedPrefStart.getString("acc", null));
            } else if (resultCode == RESULT_CANCELED) {
                //Toast.makeText(this, R.string.pick_account, Toast.LENGTH_LONG).show();o
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(mainView);
        SharedPreferences sharedPrefStart = getSharedPreferences("AccountName",
                Context.MODE_PRIVATE);

        LogIn logIn = new LogIn(player, new LogIn.AsyncResponse(){

            @Override
            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                System.out.println("player id:" + player.getId());
                System.out.println(player.getUserName());
                //intent4.putExtra("player", player);
            }
        });
        logIn.execute(sharedPrefStart.getString("acc", null));

        if(player.getNickname() == null) {

            SendNickname sendNickname = new SendNickname(player, new SendNickname.AsyncResponse() {

                @Override
                public void processFinish(String output) {
                    System.out.println("player id:" + player.getId());
                    System.out.println(player.getUserName());
                }
            });
            sendNickname.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent4 = new Intent(this, LoadingActivity.class);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    public void onDestroy() {
        SharedPreferences.Editor colorEditor;
        colorEditor = sharedPrefColor.edit();

        colorEditor.putInt("code",colorCode);
        colorEditor.apply();
        super.onDestroy();

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        gestureDetectorCompat.onTouchEvent(motionEvent);

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if(mainView.startRect.contains(motionEvent.getX(),motionEvent.getY()))
                {
                    mainView.setStartClicked(true);
                }else if (mainView.practiceRect.contains(motionEvent.getX(), motionEvent.getY()))
                {
                    mainView.setPracticeClicked(true);
                }
                else if(mainView.highScoreRect.contains(motionEvent.getX(), motionEvent.getY()))
                {
                    mainView.setHighScoreClicked(true);
                }
                else if(mainView.settingsRect.contains(motionEvent.getX(),motionEvent.getY()))
                {
                   mainView.setSettingsClicked(true);
                }
                mainView.invalidate();
                break;

            case MotionEvent.ACTION_UP:

                mainView.setStartClicked(false);
                mainView.setPracticeClicked(false);
                mainView.setHighScoreClicked(false);
                mainView.setSettingsClicked(false);
                mainView.invalidate();

                if(mainView.startRect.contains(motionEvent.getX(),motionEvent.getY()))
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
                }else if(mainView.highScoreRect.contains(motionEvent.getX(), motionEvent.getY()))
                {
                   // Intent intent3 = new Intent(this, HighScoreActivity2.class);
                   // startActivity(intent3);
                }else if(mainView.settingsRect.contains(motionEvent.getX(),motionEvent.getY()))
                {
                    Intent intent2 = new Intent(this, StartActivity.class);
                    startActivity(intent2);
                }
                break;

        }
        return true;
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void pickNickname(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Pick a nickname");
        //alert.setMessage("Message");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
       final SharedPreferences sharedPref = getSharedPreferences("Nickname", Context.MODE_PRIVATE);
        String nickname = sharedPref.getString("nickname", null);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String nickname = input.getText().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nickname", nickname);
                editor.commit();
                player.setNickname(sharedPref.getString("nickname", null));

            }
        });

        alert.show();
    }

    public void swipeLeft(){
        mainView.increaseColor();
    }

    public void swipeRight(){
        mainView.decreaseColor();
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
