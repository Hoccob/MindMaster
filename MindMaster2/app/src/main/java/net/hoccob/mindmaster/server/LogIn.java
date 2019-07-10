package net.hoccob.mindmaster.server;

import android.net.TrafficStats;
import android.os.AsyncTask;

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
        TrafficStats.setThreadStatsTag(10000);
    }

    @Override
    protected String doInBackground(String... params) throws RuntimeException{
        this.player.setUserName(params[0]);
        url =  "https://mindmaster.ee:8443/api/users/{eid}";
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("LOGIN DONE!!");
        return result;
    }


    @Override
    protected void onPostExecute(String result){
        delegate.processFinish(result);
        //System.out.println("ONPOSTEXEC");
    }
}
