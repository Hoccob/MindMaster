package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class GetOpponentScore extends AsyncTask<Integer, String, Integer> {

    //public interface AsyncResponse {
    //    void processFinish(Integer output);
    //}

    //public GetOpponentScore.AsyncResponse delegate;
//
    //public GetOpponentScore(GetOpponentScore.AsyncResponse delegate){
    //    this.delegate = delegate;
    //}

    @Override
    protected Integer doInBackground(Integer... params){
        String url;
        String output;
        Integer result = 0;

        try {

            //Create template
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            url = "http://mindmaster.ee:8080/api/lastScore/{gameId}/{userId}";

            output = restTemplate.getForObject(url, String.class, params[0], params[1]);
            JSONObject jsonOpponentScore = new JSONObject(output);
            return(jsonOpponentScore.getInt("score"));
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("GET OPPONENT SCORE DONE!!");
        return result;
    }

    @Override
    protected void onPostExecute(Integer result){
        //delegate.processFinish(result);
        super.onPostExecute(result);
    }
}
