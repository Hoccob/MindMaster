package net.hoccob.mindmaster;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MultiPlayerActivity extends Activity {

    MultiPlayerView multiPlayerView;
    Player player;
    Waitlist waitlist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.enableDefaults();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        multiPlayerView = new MultiPlayerView(this, size.x, size.y);
        player = new Player();
        waitlist = new Waitlist();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(multiPlayerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //singlePlayerView.draw();
        multiPlayerView.startGame();
        setContentView(multiPlayerView);
        hideSystemUI();

        logIn();
    }

    @Override
    protected void onPause(){
        super.onPause();
        //multiPlayerView.pause();
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


    private void logIn(){
        String url = "http://mindmaster.ee:8080/api/users/{userName}";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        player.setUserName("testUser3");
        String output = restTemplate.getForObject(url, String.class, player.getUserName());

        System.out.println(output);
        try {
            JSONObject jsonPlayer = new JSONObject(output);
            player.setId(jsonPlayer.getInt("id"));
            player.setUserName(jsonPlayer.getString("userName"));
            player.setPoints(jsonPlayer.getInt("points"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if(player.getId() > 0){
            requestGame();
        }

    }

    private void requestGame(){

        Boolean gotGame = false;
        String output;

        String url = "http://mindmaster.ee:8080/api/waitlist";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonWaitlist = new JSONObject();

        try {
            jsonWaitlist.put("userId", player.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpEntity<String> entity = new HttpEntity<>(jsonWaitlist.toString(),headers);

        restTemplate.postForObject(url, entity, String.class);

        url = url + "/" + player.getId();

        while(!gotGame){
            try {
                Thread.sleep(2000);
                output = restTemplate.getForObject(url, String.class);
                jsonWaitlist = new JSONObject(output);
                waitlist.setGameId(jsonWaitlist.getInt("gameId"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(waitlist.getGameId() > 0){
                gotGame = true;
            }
        }

        multiPlayerView.setPlay(true);

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
