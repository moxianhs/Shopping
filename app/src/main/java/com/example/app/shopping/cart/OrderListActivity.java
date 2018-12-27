package com.example.app.shopping.cart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import io.objectbox.Box;

public class OrderListActivity extends AppCompatActivity {

    private static final String TAG = "OrderListActivity";
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        toolbarInit();
        contentInit();
    }

    private void toolbarInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void contentInit() {
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new OrderAdapter());
        Log.e(TAG, "contentInit: " + recyclerView.getAdapter().getItemCount());
    }
}
