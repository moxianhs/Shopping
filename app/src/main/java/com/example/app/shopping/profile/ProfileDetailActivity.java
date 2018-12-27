package com.example.app.shopping.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.User;

import java.util.Objects;

import io.objectbox.Box;

public class ProfileDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        toolbarInit();

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView gender = findViewById(R.id.gender);
        TextView age = findViewById(R.id.age);
        TextView mobile = findViewById(R.id.mobile);
        TextView address = findViewById(R.id.address);


        Box<User> userBox = ShoppingApplication.boxStore.boxFor(User.class);
        User user = userBox.get(ShoppingApplication.currentUserId);

        username.setText(user.getUsername());
        password.setText(user.getPassword());
        gender.setText(user.getGender() == 1 ? "男" : "女");
        age.setText((String.valueOf(user.getAge())));
        mobile.setText(user.getMobile());
        address.setText(user.getAddress());

    }

    private void toolbarInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        MenuItem edit = menu.findItem(R.id.edit);

        edit.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            startActivity(intent);

            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }
}
