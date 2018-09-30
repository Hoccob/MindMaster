package net.hoccob.mindmaster;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    public int y;
    MainView mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mainView = new MainView(this, size.x, size.y);
        y = size.y;


    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(mainView);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    Intent intent3 = new Intent(this, HighScoreActivity.class);
                    startActivity(intent3);
                }
                else if(motionEvent.getX() > 0 && (motionEvent.getX() < 601)&& motionEvent.getY() > (y/10 * 7) && motionEvent.getY() < ((y/10 * 7) + 300))
                {
                    Intent intent4 = new Intent(this, LoadingActivity.class);
                    startActivity(intent4);
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
