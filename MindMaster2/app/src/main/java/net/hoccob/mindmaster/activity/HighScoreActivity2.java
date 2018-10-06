package net.hoccob.mindmaster.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.view.FirstFragment;
import net.hoccob.mindmaster.view.MultiPlayerView;
import net.hoccob.mindmaster.view.SecondFragment;

import android.support.v4.app.FragmentManager;
import android.view.View;


public class HighScoreActivity2 extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    String SinglePlayerScore;
    String MultiPlayerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity2);

        SharedPreferences sharedPref = getSharedPreferences("HighScore",
                Context.MODE_PRIVATE);
        int highScore1 = sharedPref.getInt("1", 0);
        int highScore2 = sharedPref.getInt("2", 0);
        int highScore3 = sharedPref.getInt("3", 0);
        int highScore4 = sharedPref.getInt("4", 0);
        int highScore5 = sharedPref.getInt("5", 0);
        SinglePlayerScore = "1." + String.valueOf(highScore1) + "\n2." + String.valueOf(highScore2) + "\n3." + String.valueOf(highScore3) + "\n4." + String.valueOf(highScore4) +"\n5." +String.valueOf(highScore5);

        SharedPreferences sharedPref2 = getSharedPreferences("HighScoreMultiplayer",
                Context.MODE_PRIVATE);
        int highScore1a = sharedPref2.getInt("1", 0);
        int highScore2a = sharedPref2.getInt("2", 0);
        int highScore3a = sharedPref2.getInt("3", 0);
        int highScore4a = sharedPref2.getInt("4", 0);
        int highScore5a = sharedPref2.getInt("5", 0);
        MultiPlayerScore = "1." + String.valueOf(highScore1a) + "\n2." + String.valueOf(highScore2a) + "\n3." + String.valueOf(highScore3a) + "\n4." + String.valueOf(highScore4a) +"\n5." +String.valueOf(highScore5a);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new FirstFragment(SinglePlayerScore);
            }
            else{
                return new SecondFragment(MultiPlayerScore);
            }

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
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
