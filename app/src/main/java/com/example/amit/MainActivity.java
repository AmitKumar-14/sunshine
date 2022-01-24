package com.example.amit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RelativeLayout homeRL;
    ProgressBar pb;
    ImageView Icon,backIV,menu;
    TextView date,maxTemp,location,sunrise,sunset,iconText;
    RecyclerView recyclerView;
   WeatherAdapter weatherAdapter;
    ArrayList<WeatherModel> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        setContentView(R.layout.activity_main);
        homeRL=(RelativeLayout) findViewById(R.id.homeRL);
        Icon=(ImageView) findViewById(R.id.Icon);
        date = (TextView) findViewById(R.id.date);
        maxTemp = (TextView) findViewById(R.id.maxTemp);

        location = (TextView) findViewById(R.id.location);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        iconText = (TextView) findViewById(R.id.iconText);
        backIV =(ImageView) findViewById(R.id.idIVback);
        pb =(ProgressBar) findViewById(R.id.pb);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        menu=(ImageView) findViewById(R.id.menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList=new ArrayList<WeatherModel>();

        weatherAdapter=new WeatherAdapter(arrayList,this);
        recyclerView.setAdapter(weatherAdapter);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        weatherInfo();
    }

    private void weatherInfo(){
        String url="https://api.weatherapi.com/v1/forecast.json?key=9578167126064e38b90171531210608&q=hamirpur&days=5&aqi=yes&alerts=yes";

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.setVisibility(View.GONE);


               homeRL.setVisibility(View.VISIBLE);

                try {
                    JSONObject loaction =response.getJSONObject("location");
                   String name= loaction.getString("name");
                   String region =loaction.getString("region");
                   location.setText(name+", "+region);
                    JSONObject current=response.getJSONObject("current");
                   String inputdate=current.getString("last_updated");

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    SimpleDateFormat output = new SimpleDateFormat("MMM,dd ");

                  Date t =input.parse(inputdate);
                  date.setText(output.format(t));

                   maxTemp.setText(current.getString("temp_c")+"°c");



                    String text=current.getJSONObject("condition").getString("text");
                    iconText.setText(text);
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("https:".concat(conditionIcon)).resize(220,220).into(Icon);

                    int isDay = response.getJSONObject("current").getInt("is_day");
                    if(isDay==1){
                        //morning
                        Picasso.get().load("https://images.unsplash.com/photo-1622396481328-9b1b78cdd9fd?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80").into(backIV);
                    } else{
                        Picasso.get().load("https://images.unsplash.com/photo-1489549132488-d00b7eee80f1?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80").into(backIV);
                    }

                    sunrise.setText("sunrise - "+response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONObject("astro").getString("sunrise"));
                    sunset.setText("sunset - "+response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONObject("astro").getString("sunset"));


                    JSONArray hourArray=response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour");

                    for(int i=0;i<hourArray.length();i++){
                        JSONObject hourObject=hourArray.getJSONObject(i);
                         String ico=hourObject.getJSONObject("condition").getString("icon");
                         String dat=hourObject.getString("time");
                         String conditionTex=hourObject.getJSONObject("condition").getString("text");
                         String maxTem=hourObject.getString("temp_c");
                         String minTem=hourObject.getString("wind_kph");

                         arrayList.add(new WeatherModel(ico,dat,conditionTex,maxTem,minTem));


                    }
                    weatherAdapter.notifyDataSetChanged();


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag",error.toString(),error);
                Toast.makeText(MainActivity.this,"Please connect to Internet",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public void dayForecast(View view) {
        Intent intent=new Intent(MainActivity.this,ForecastDay.class);
        startActivity(intent);
    }
}