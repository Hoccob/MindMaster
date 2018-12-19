package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class GetOpponentScore extends AsyncTask<Integer, String, String> {

    //public interface AsyncResponse {
    //    void processFinish(Integer output);
    //}

    //public GetOpponentScore.AsyncResponse delegate;
//
    //public GetOpponentScore(GetOpponentScore.AsyncResponse delegate){
    //    this.delegate = delegate;
    //}

    @Override
    protected String doInBackground(Integer... params){
        String url;
        String output;

        try {

            //Create template
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            url = "http://mindmaster.ee:8080/api/lastScore/{gameId}/{userId}";

            output = restTemplate.getForObject(url, String.class, params[0], params[1]);
            //JSONObject jsonOpponentScore = new JSONObject(output);
            if(output == null || output.isEmpty()){
                output = "0";
            }
            return(output);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        System.out.println("GET OPPONENT SCORE DONE!!");
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        //delegate.processFinish(result);
        super.onPostExecute(result);
    }
}
