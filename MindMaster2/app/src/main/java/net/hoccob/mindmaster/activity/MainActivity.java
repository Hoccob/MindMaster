package net.hoccob.mindmaster.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.server.LogIn;
import net.hoccob.mindmaster.server.SendNickname;
import net.hoccob.mindmaster.view.MainView;

public class MainActivity extends Activity {

    public int y;
    MainView mainView;
    Player player;

    Intent intent4;
    int i;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        SendNickname sendNickname = new SendNickname(player, new SendNickname.AsyncResponse(){

            @Override
            public void processFinish(String output){
                //Here you will receive the result fired from async class
                //of onPostExecute(result) method.
                System.out.println("player id:" + player.getId());
                System.out.println(player.getUserName());
                //intent4.removeExtra("player");
                //intent4.putExtra("player", player);
            }
        });
        sendNickname.execute();

        mainView = new MainView(this, size.x, size.y);
        y = size.y;

        intent4 = new Intent(this, LoadingActivity.class);

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
                //Toast.makeText(this, R.string.pick_account, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(mainView);
        SharedPreferences sharedPrefStart = getSharedPreferences("AccountName",
                Context.MODE_PRIVATE);

        LogIn logIn= new LogIn(player, new LogIn.AsyncResponse(){

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
                    if(player.getId() > 0 && player.getNickname() != null) {
                        //Intent intent4 = new Intent(this, LoadingActivity.class);
                        System.gc();
                        intent4.putExtra("player", player);
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

                // Do something with value!
            }
        });

       /* alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });*/

        alert.show();
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
