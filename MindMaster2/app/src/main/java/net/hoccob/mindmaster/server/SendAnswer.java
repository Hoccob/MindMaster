package net.hoccob.mindmaster.server;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class SendAnswer extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... params){
        String result = "";
        String url =  "http://mindmaster.ee:8080/api/gameplay";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        JSONObject jsonAnswer = new JSONObject();
        try {
            jsonAnswer.put("equationId", params[0]);
            jsonAnswer.put("userId", params[1]);
            jsonAnswer.put("gameId", params[2]);
            jsonAnswer.put("givenAnswer", params[3]);
            jsonAnswer.put("calcTimeMillis", params[4]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Configure for POST request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonAnswer.toString(),headers);

        restTemplate.postForEntity(url, entity, String.class);

        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        System.out.println("Answer sent!");
    }
}
