package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.Player;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LogIn extends AsyncTask<String, String, String> {

    private String url;
    private Player player;
    public LogIn.AsyncResponse delegate;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public LogIn(Player player, AsyncResponse delegate){
        this.delegate = delegate;
        this.player = player;
    }

    @Override
    protected String doInBackground(String... params) throws RuntimeException{
        this.player.setUserName(params[0]);
        url =  "http://mindmaster.ee:8080/api/users/{userId}";
        String result = "";

        //Create template
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        //GET player by userName
        try {
            result = restTemplate.getForObject(url, String.class, player.getUserName());
            JSONObject jsonPlayer = new JSONObject(result);
            player.setId(jsonPlayer.getInt("id"));
            //player.setUserName(jsonPlayer.getString("userName"));
            player.setPoints(jsonPlayer.getInt("points"));
        }catch(RuntimeException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("BG DONE");
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        delegate.processFinish(result);
        System.out.println("ONPOSTEXEC");
    }
}
