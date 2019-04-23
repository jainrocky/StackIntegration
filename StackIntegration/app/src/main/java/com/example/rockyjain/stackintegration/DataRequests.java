package com.example.rockyjain.stackintegration;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.rockyjain.stackintegration.AppConstants.CONNECTION_TIMEOUT;
import static com.example.rockyjain.stackintegration.AppConstants.READ_TIMEOUT;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_ACTIVITY;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_API;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_METHOD;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_POPULAR_TAG;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_SITE;
import static com.example.rockyjain.stackintegration.AppConstants.REQUEST_TAGS;

public class DataRequests {

    static List<Question> getTagQuestionList(String tag1, String tag2, String tag3, String tag4){
        List<Question> questions = new ArrayList<>();
        try {
            URL url = new URL(REQUEST_API + REQUEST_ACTIVITY + REQUEST_TAGS + tag1+";"+tag2+";"+tag3+";"+tag4 + REQUEST_SITE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod(REQUEST_METHOD);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.i("MyTag", "Connection Code:" + responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String page = stringBuilder.toString();
            inputStream.close();
            connection.disconnect();
            JSONObject object = new JSONObject(page);
            JSONArray items = object.getJSONArray("items");
            int len = items.length();
            if(items!=null && len > 0){
                for(int i=0; i<len; i++){
                    JSONObject item = items.getJSONObject(i);
                    Question question = new Question();
                    question.setQuestion_id(item.getString("question_id"));
                    question.setTitle(item.getString("title"));
                    question.setLink(item.getString("link"));
                    question.setView_count(item.getString("view_count"));
                    if(item.getJSONObject("owner").getString("user_type").equals("registered"))
                        question.setOwner_profile_image(item.getJSONObject("owner").getString("profile_image"));
                    questions.add(question);
                    Log.i("MyTag", ""+question.getQuestion_id());
                }
            }else
                Log.i("MyTag", "EMPTY!");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    static List<String> getPopularTag(){
        List<String> tags = new ArrayList<>();
        try {
            URL url = new URL(REQUEST_POPULAR_TAG);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod(REQUEST_METHOD);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.i("MyTag", "Connection Code:" + responseCode);
                throw new IOException("HTTP error code: " + responseCode);
            }
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String page = stringBuilder.toString();
            inputStream.close();
            connection.disconnect();
            JSONObject object = new JSONObject(page);
            JSONArray items = object.getJSONArray("items");
            int len = items.length();
            if(items!=null && len > 0){
                for(int i=0; i<len; i++){
                    JSONObject item = items.getJSONObject(i);
                    String tag = item.getString("name");
                    tags.add(tag);
                }
            }else
                Log.i("MyTag", "EMPTY!");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tags;
    }

}
