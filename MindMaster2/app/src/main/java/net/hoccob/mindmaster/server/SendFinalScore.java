package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SendFinalScore extends AsyncTask<Integer, String, String> {

    @Override
    protected String doInBackground(Integer... params){
        String result = "";
        String url =  "http://mindmaster.ee:8080/api/";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            JSONObject jsonScore = new JSONObject();
            jsonScore.put("gameId", params[0]);
            jsonScore.put("userId", params[1]);
            jsonScore.put("score", params[2]);

            //Configure for POST request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonScore.toString(), headers);

            restTemplate.postForEntity(url + "score", entity, String.class);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(RuntimeException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        System.out.println("Final score sent!");
    }
}
