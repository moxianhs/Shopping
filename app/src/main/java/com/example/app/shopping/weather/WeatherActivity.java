package com.example.app.shopping.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    private static final String TAG = "WeatherActivity";
    private MutableLiveData<Map<String, Object>> mData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 0);
        }

        toolbarInit();

    }


    private void toolbarInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
        contentInit();
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("unchecked")
    private void contentInit() {
        mData.observe(this, stringObjectMap -> {
            assert stringObjectMap != null;

            Map<String, Object> cityInfo = (Map<String, Object>) stringObjectMap.get("cityInfo");
            CardView cityCard = findViewById(R.id.city_card);
            cityCard.setVisibility(View.VISIBLE);
            TextView cityView = findViewById(R.id.city_name);
            cityView.setText(cityInfo.get("parent") + " " + cityInfo.get("city"));

            Map<String, Object> data = (Map<String, Object>) stringObjectMap.get("data");
            List<Map<String, Object>> forecast = (List<Map<String, Object>>) data.get("forecast");
            Map<String, Object> today = forecast.get(0);
            TextView type = findViewById(R.id.weather_type);
            type.setText((String) today.get("type"));

            TextView temperatureView = findViewById(R.id.temperature);
            temperatureView.setText(today.get("high") + "\t" + today.get("low"));

            TextView quality = findViewById(R.id.quality);
            quality.setText("空气质量: " + data.get("quality"));

            TextView wind = findViewById(R.id.wind);
            wind.setText(today.get("fx") + ": " + today.get("fl"));
        });
    }

    private void dataInit(String cityName) {
        AsyncTask.execute(() -> {
            Log.e(TAG, "dataInit: Start loading data");
            List<City> cities = new ArrayList<>();
            String code = "";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("city.json")));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null && line.length() > 0) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }

                City[] array = new Gson().fromJson(sb.toString(), City[].class);
                cities.addAll(Arrays.asList(array));
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (City city : cities) {
                if (cityName.equals(city.getName())) {
                    code = city.getCode();
                }
            }

            if ("".equals(code)) {
                Log.e(TAG, "dataInit: 没有你要找的城市: " + cityName);
                return;
            }

            Log.e(TAG, "dataInit: 开始网络连接......");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherService service = retrofit.create(WeatherService.class);

            Call<HashMap<String, Object>> call = service.getWeather(code);

            try {
                Log.e(TAG, "dataInit: 执行到这了......" + call.request().url());
                Response<HashMap<String, Object>> response = call.execute();
                Log.e(TAG, "dataInit: " + response);
                HashMap<String, Object> weatherResponse = response.body();
                assert weatherResponse != null;
                double state = (double) weatherResponse.get("status");
                if (state == 200) {
                    mData.postValue(weatherResponse);
                } else {
                    Log.e(TAG, "dataInit: " + weatherResponse.get("message"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(30000);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("请输入城市名");
        SearchView.SearchAutoComplete hint = searchView.findViewById(R.id.search_src_text);
        hint.setHintTextColor(Color.WHITE);
        hint.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                dataInit(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
