package net.hoccob.mindmaster.server;

import android.net.TrafficStats;
import android.os.AsyncTask;

import com.google.android.gms.common.api.Response;

import net.hoccob.mindmaster.Equation;
import net.hoccob.mindmaster.Game;
import net.hoccob.mindmaster.Player;
import net.hoccob.mindmaster.Waitlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class RequestGame extends AsyncTask<String, String, String>{
    private String url;
    private String body;
    private Player player;
    private Waitlist waitlist;
    private ArrayList<ArrayList<Equation>> equations;
    private Game game;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate;

    public RequestGame(Player player, Waitlist waitlist, ArrayList<ArrayList<Equation>> equations, Game game, AsyncResponse delegate){
        this.delegate = delegate;
        this.player = player;
        this.waitlist = waitlist;
        this.equations = equations;
        this.game = game;
        TrafficStats.setThreadStatsTag(1);
    }

    @Override
    protected String doInBackground(String... params){
        //this.body = params[0];
        //url =  "https://mindmaster.ee:8443/api/users/{userId}";
        String result = "";
        Boolean gotGame = false;
        String output;
        String opponentNickname = "";

        ResponseEntity dummyResult;

        try {

            //Create template
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            JSONObject jsonWaitlist = new JSONObject();
            jsonWaitlist.put("userId", player.getId());
            jsonWaitlist.put("nickname", player.getNickname());

            //Configure for POST request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonWaitlist.toString(), headers);

            TrafficStats.setThreadStatsTag(1);
            //POST for waitlist
            url = "https://mindmaster.ee:8443/api/waitlist";
            System.out.println("Posting for game");
            dummyResult = restTemplate.postForEntity(url, entity, String.class);
            String dummy = dummyResult.toString();

            //game.setId(0);

            url = "https://mindmaster.ee:8443/api/waitlist/{userId}";
            while (!gotGame) {
                System.out.println("LOOKING FOR GAME!!");
                if (isCancelled()) {
                    System.out.println("LOADoING CANCELLED!");
                    return null;
                }
                    try {
                        Thread.sleep(2000);
                        output = restTemplate.getForObject(url, String.class, player.getId());
                        jsonWaitlist = new JSONObject(output);
                        if(!jsonWaitlist.isNull("gameId")) {
                            game.setId(jsonWaitlist.getInt("gameId"));
                        }
                        System.out.println("WAITLIST GAMEID: " + game.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                if (game.getId() != null) {
                    gotGame = true;
                }
            }

            url = "https://mindmaster.ee:8443/api/waitlist/{gameId}/{userId}";
            try {
                output = restTemplate.getForObject(url, String.class, game.getId(), player.getId());
                JSONObject jsonOpponent = new JSONObject(output);
                game.setOpponentNickname(jsonOpponent.getString("nickname"));
            }catch (RuntimeException e){
                e.printStackTrace();
            }

            url = "https://mindmaster.ee:8443/api/equations/{gameId}";

            result = restTemplate.getForObject(url, String.class, game.getId());
                JSONArray jsonEquations = new JSONArray(result);
                for (int i = 0; i < 12; i++) {
                    equations.add(new ArrayList<Equation>());
                    for (int j = 0; j < 25; j++) {
                        System.out.println("i: " + i + " j: " + j);
                        equations.get(i).add(new Equation(jsonEquations.getJSONObject((i * 25) + j).getInt("equationId"), jsonEquations.getJSONObject((i * 25) + j).getInt("level"), jsonEquations.getJSONObject((i * 25) + j).getInt("operand_1"), jsonEquations.getJSONObject((i * 25) + j).getInt("operand_2")));
                        equations.get(i).get(j).calcAnswer();
                    }
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        System.out.println("REQUEST GAME DONE!!");
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        delegate.processFinish(result);
    }
}
