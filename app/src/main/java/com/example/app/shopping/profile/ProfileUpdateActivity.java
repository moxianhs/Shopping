package com.example.app.shopping.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.User;

import io.objectbox.Box;

public class ProfileUpdateActivity extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private RadioGroup gender;
    private EditText age;
    private EditText mobile;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        initToolbar();
        contentInit();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void contentInit() {
        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        confirmPassword = findViewById(R.id.confirm_password_field);
        gender = findViewById(R.id.gender_group);
        age = findViewById(R.id.age_field);
        mobile = findViewById(R.id.mobile_field);
        address = findViewById(R.id.address_field);
    }

    private boolean update() {
        if (!password.getText().toString().equals(confirmPassword.getText().toString()))
            return false;

        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setGender((gender.getCheckedRadioButtonId() == R.id.male) ? 1 : 0);
        user.setAge(Integer.parseInt(age.getText().toString()));
        user.setMobile(mobile.getText().toString());
        user.setAddress(address.getText().toString());

        Box<User> userBox = ShoppingApplication.boxStore.boxFor(User.class);
        user.setId(ShoppingApplication.currentUserId);
        userBox.put(user);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);

        MenuItem done = menu.findItem(R.id.done);

        done.setOnMenuItemClickListener(item -> {
                    if (update()) {
                        Intent intent = new Intent(this, ProfileDetailActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                    }

                    return false;
                }
        );

        return super.onCreateOptionsMenu(menu);
    }
}
