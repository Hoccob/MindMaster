package net.hoccob.mindmaster.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.server.LogIn;
import net.hoccob.mindmaster.view.MainView;

public class MainActivity extends Activity {

    public int y;
    MainView mainView;
    Player player;

    Intent intent4;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Account[] accounts = GetAccount();
        System.out.print("TESTINGTESTING");
        System.out.print(accounts.length);
        System.out.print("\n");
        mainView = new MainView(this, size.x, size.y);
        y = size.y;
        player = new Player();
        intent4 = new Intent(this, LoadingActivity.class);
    }
    private Account[] GetAccount(){
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        return(accounts);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(mainView);

        LogIn logIn= new LogIn(player, new LogIn.AsyncResponse(){

            @Override
            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                System.out.println("player id:" + player.getId());
                intent4.putExtra("player", player);
            }
        });
        logIn.execute("testuser1");
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
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if (motionEvent.getX() > 0 && (motionEvent.getX() < 601) && motionEvent.getY() > y/10  && motionEvent.getY() < (y/10 + 300))
                {
                    Intent intent = new Intent(this, SinglePlayerActivity.class);
                    startActivity(intent);
                }
                else if (motionEvent.getX() > 0 && (motionEvent.getX() < 601)&& motionEvent.getY() > y/10 * 3 && motionEvent.getY() < (y/10 * 3 + 300))
                {
                    Intent intent2 = new Intent(this, PracticeActivity.class);
                    startActivity(intent2);
                }
                else if(motionEvent.getX() > 0 && (motionEvent.getX() < 601)&& motionEvent.getY() > y/2 &&motionEvent.getY() < (y/2 + 300))
                {
                    Intent intent3 = new Intent(this, HighScoreActivity2.class);
                    startActivity(intent3);
                }
                else if(motionEvent.getX() > 0 && (motionEvent.getX() < 601)&& motionEvent.getY() > (y/10 * 7) && motionEvent.getY() < ((y/10 * 7) + 300))
                {
                    if(player.getId() > 0) {
                        //Intent intent4 = new Intent(this, LoadingActivity.class);
                        System.gc();
                        startActivity(intent4);
                    }else{
                        Toast.makeText(this, "Not logged in!", Toast.LENGTH_LONG).show();
                    }
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
