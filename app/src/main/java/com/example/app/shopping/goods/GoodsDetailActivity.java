package com.example.app.shopping.goods;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;

import java.util.Objects;

import io.objectbox.Box;

public class GoodsDetailActivity extends AppCompatActivity {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        toolbarInit();

        long id = getIntent().getLongExtra("ID", 0);

        Box<Goods> box = ShoppingApplication.boxStore.boxFor(Goods.class);
        Goods goods = box.get(id);
        if (goods != null) {
            AppCompatImageView imageView = findViewById(R.id.image);
            Glide.with(this)
                    .load(goods.getPic())
                    .into(imageView);
            TextView name = findViewById(R.id.name);
            name.setText(goods.getName());

            TextView price = findViewById(R.id.price);
            price.setText("￥" + goods.getPrice());

            TextView comment = findViewById(R.id.comment);
            comment.setText(goods.getComment());


            Button add = findViewById(R.id.add);
            Button remove = findViewById(R.id.remove);
            TextView count = findViewById(R.id.count);

            MutableLiveData<Integer> sum = new MutableLiveData<>();
            sum.observe(this, v -> count.setText(String.valueOf(v)));
            sum.postValue(0);
            add.setOnClickListener(v -> {
                if (sum.getValue() != null) {
                    int newValue = sum.getValue() + 1;
                    sum.postValue(newValue);
                }

            });
            remove.setOnClickListener(v -> {
                if (sum.getValue() != null) {
                    int newValue = sum.getValue() - 1;
                    if (newValue >= 0)
                        sum.postValue(newValue);
                }

            });

            Button buy = findViewById(R.id.buy);
            Button like = findViewById(R.id.like);
            buy.setOnClickListener(v -> {
                // todo jump to order list
            });
            like.setOnClickListener(v -> {
                // todo jump to cart list
            });
        }
    }


    private void toolbarInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
