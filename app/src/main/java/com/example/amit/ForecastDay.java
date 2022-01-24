package com.example.amit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastDay extends AppCompatActivity {
     ArrayList<ForecastModel> arrayList;
    RecyclerView recyclerView;
    ForecastAdapter forecastAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_day);
        Intent intent = getIntent();
       // String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        recyclerView=findViewById(R.id.frecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<ForecastModel>();
        forecastAdapter=new ForecastAdapter(arrayList,this);
        recyclerView.setAdapter(forecastAdapter);
        getForecastInfo();
    }

    void getForecastInfo(){
        String url="https://api.weatherapi.com/v1/forecast.json?key=9578167126064e38b90171531210608&q=hamirpur&days=5&aqi=yes&alerts=yes";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("forecast");
                    JSONArray jsonArray=jsonObject.getJSONArray("forecastday");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject forecastObject=jsonArray.getJSONObject(i);
                        String date=forecastObject.getString("date");
                        String temp=forecastObject.getJSONObject("day").getString("maxtemp_c");
                        String icon=forecastObject.getJSONObject("condition").getString("icon");
                        String wind=forecastObject.getJSONObject("day").getString("maxwind_kph");
                        String humidity=forecastObject.getJSONObject("day").getString("avghumidity");
                        String conditionText=forecastObject.getJSONObject("condition").getString("text");

                        arrayList.add(new ForecastModel(date,icon,temp,wind,humidity,conditionText));

                    }

                    forecastAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag",error.toString(),error);
                Toast.makeText(ForecastDay.this,"error occured",Toast.LENGTH_SHORT).show();
            }
        });

         requestQueue.add(jsonObjectRequest);

    }
}