package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SendAnswer extends AsyncTask<Integer, String, String> {


    @Override
    protected String doInBackground(Integer... params){
        String result = "";
        String url =  "https://mindmaster.ee:8443/api/gameplay";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            JSONObject jsonAnswer = new JSONObject();
                jsonAnswer.put("equationId", params[0]);
                jsonAnswer.put("userId", params[1]);
                jsonAnswer.put("gameId", params[2]);
                jsonAnswer.put("givenAnswer", params[3]);
                jsonAnswer.put("calcTimeMillis", params[4]);

            //Configure for POST request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonAnswer.toString(), headers);

            restTemplate.postForEntity(url, entity, String.class);

            url = "https://mindmaster.ee:8443/api/lastScore";

            JSONObject jsonLastScore = new JSONObject();
            jsonLastScore.put("gameId", params[2]);
            jsonLastScore.put("userId", params[1]);
            jsonLastScore.put("score", params[5]);
            jsonLastScore.put("timer", params[6]);

            entity = new HttpEntity<>(jsonLastScore.toString(), headers);

            restTemplate.postForEntity(url, entity, String.class);

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
        System.out.println("Answer sent!");
    }
}
