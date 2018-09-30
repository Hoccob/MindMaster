package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.Waitlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class RequestGame extends AsyncTask<String, String, String>{
    private String url;
    private String body;
    private Player player;
    private Waitlist waitlist;
    private ArrayList<ArrayList<Equation>> equations;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate;

    public RequestGame(Player player, Waitlist waitlist, ArrayList<ArrayList<Equation>> equations, AsyncResponse delegate){
        this.delegate = delegate;
        this.player = player;
        this.waitlist = waitlist;
        this.equations = equations;
    }

    @Override
    protected String doInBackground(String... params){
        this.body = params[0];
        url =  "http://mindmaster.ee:8080/api/users/{userId}";
        String result;
        Boolean gotGame = false;
        String output;

        //Create template
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        //GET player by userName
        result = restTemplate.getForObject(url, String.class, body);

        //Update player object
        try {
            JSONObject jsonPlayer = new JSONObject(result);
            player.setId(jsonPlayer.getInt("id"));
            player.setUserName(jsonPlayer.getString("userName"));
            player.setPoints(jsonPlayer.getInt("points"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonWaitlist = new JSONObject();
        try {
            jsonWaitlist.put("userId", player.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Configure for POST request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonWaitlist.toString(),headers);

        //POST for waitlist
        url = "http://mindmaster.ee:8080/api/waitlist";
        restTemplate.postForEntity(url, entity, String.class);

        waitlist.setGameId(0);

        url = "http://mindmaster.ee:8080/api/waitlist/{userId}";
        while(!gotGame){
            try {
                Thread.sleep(4000);
                output = restTemplate.getForObject(url, String.class, player.getId());
                jsonWaitlist = new JSONObject(output);
                waitlist.setGameId(jsonWaitlist.getInt("gameId"));
                System.out.println("WAITLIST GAMEID: " + waitlist.getGameId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (RuntimeException e){
                e.printStackTrace();
            }

            if(waitlist.getGameId() > 0){
                gotGame = true;
            }
        }

        url = "http://mindmaster.ee:8080/api/equations/{gameId}";

        result = restTemplate.getForObject(url, String.class, waitlist.getGameId());
        try {
            JSONArray jsonEquations = new JSONArray(result);
            for(int i = 0; i < 12; i ++){
                equations.add(new ArrayList<Equation>());
                for(int j = 0; j < 25; j++){
                    System.out.println("i: " + i + " j: " + j);
                    equations.get(i).add( new Equation(jsonEquations.getJSONObject((i*25)+j).getInt("id"), jsonEquations.getJSONObject((i * 25) + j).getInt("level"), jsonEquations.getJSONObject((i * 25) + j).getInt("operand_1"),jsonEquations.getJSONObject((i * 25) + j).getInt("operand_2")));
                    equations.get(i).get(j).calcAnswer();
                }
            }
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
