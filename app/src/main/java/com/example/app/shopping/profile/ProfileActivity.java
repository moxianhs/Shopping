package com.example.app.shopping.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.User;

import io.objectbox.Box;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView username = findViewById(R.id.username);


        CardView[] cardViews = new CardView[]{
                findViewById(R.id.login_button),
                findViewById(R.id.logout_button),
                findViewById(R.id.message_button),
                findViewById(R.id.order_button),
                findViewById(R.id.history_button)
        };

        if (shouldLogin()) {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[1].setVisibility(View.GONE);
            cardViews[2].setVisibility(View.GONE);
            cardViews[3].setVisibility(View.GONE);
            cardViews[4].setVisibility(View.GONE);
            username.setText("未登录");
        } else {
            cardViews[0].setVisibility(View.GONE);
            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[2].setVisibility(View.VISIBLE);
            cardViews[3].setVisibility(View.VISIBLE);
            cardViews[4].setVisibility(View.VISIBLE);
            Box<User> userBox = ShoppingApplication.boxStore.boxFor(User.class);
            User user = userBox.get(ShoppingApplication.currentUserId);
            username.setText(user.getUsername());

        }

        cardViews[0].setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        cardViews[1].setOnClickListener(v -> {
            ShoppingApplication.currentUserId = -1;
        });

        cardViews[2].setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileDetailActivity.class);
            startActivity(intent);
        });

    }

    private boolean shouldLogin() {
        return ShoppingApplication.currentUserId < 0;
    }
}
