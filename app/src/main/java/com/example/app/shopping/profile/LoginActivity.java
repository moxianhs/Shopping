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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.User;
import com.example.app.shopping.entity.User_;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        contentInit();
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

    private void contentInit() {
        usernameEdit = findViewById(R.id.username_field);
        passwordEdit = findViewById(R.id.password_field);
    }


    public boolean login() {
        Box<User> userBox = ShoppingApplication.boxStore.boxFor(User.class);
        Query query = userBox.query().equal(User_.username, usernameEdit.getText().toString()).build();
        User user = (User) query.findUnique();
        if (user != null)
            ShoppingApplication.currentUserId = user.getId();
        return user != null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        MenuItem done = menu.findItem(R.id.done);

        done.setOnMenuItemClickListener(item -> {
                    if (login()) {
                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
        );

        return super.onCreateOptionsMenu(menu);
    }
}
