package com.example.app.shopping.profile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app.shopping.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();

        TextView registerHint = findViewById(R.id.registerHint);
        registerHint.setOnClickListener(v -> {
            TextView view = (TextView) v;
            view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            view.getPaint().setAntiAlias(true);
            view.setTextColor(Color.BLUE);

            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        MenuItem done = menu.findItem(R.id.done);

        done.setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return false;
                }
        );

        return super.onCreateOptionsMenu(menu);
    }
}
