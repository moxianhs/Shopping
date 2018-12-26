package com.example.app.shopping.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        } else {
            cardViews[0].setVisibility(View.GONE);
            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[2].setVisibility(View.VISIBLE);
            cardViews[3].setVisibility(View.VISIBLE);
            cardViews[4].setVisibility(View.VISIBLE);
        }
    }

    private boolean shouldLogin() {
        return ShoppingApplication.currentUserId < 0;
    }
}
