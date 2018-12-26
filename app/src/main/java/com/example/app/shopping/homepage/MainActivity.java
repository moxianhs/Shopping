package com.example.app.shopping.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.app.shopping.R;
import com.example.app.shopping.cart.CartActivity;
import com.example.app.shopping.goods.GoodsListActivity;
import com.example.app.shopping.profile.ProfileActivity;
import com.example.app.shopping.weather.WeatherActivity;

/**
 *  首页
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initContent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initContent() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new RecyclerAdapter(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);

        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Intent intent;

            switch (menuItem.getItemId()) {
                default:
                case R.id.bottom_homepage:
                    break;
                case R.id.bottom_weather:
                    intent = new Intent(MainActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bottom_cart:
                    intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bottom_profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;
            }


            return true;
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
        searchView.setQueryHint("请输入关键字");
        SearchView.SearchAutoComplete hint = searchView.findViewById(R.id.search_src_text);
        hint.setHintTextColor(Color.WHITE);
        hint.setTextColor(Color.WHITE);
        return super.onCreateOptionsMenu(menu);
    }
}
