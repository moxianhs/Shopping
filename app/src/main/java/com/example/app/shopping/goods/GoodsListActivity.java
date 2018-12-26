package com.example.app.shopping.goods;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.app.shopping.R;

import java.util.Objects;

public class GoodsListActivity extends AppCompatActivity {
    private String mKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        mKeyword = getIntent().getStringExtra("keyword");
        toolbarInit();
        recyclerInit();
    }

    private void toolbarInit() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }


    private void recyclerInit() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new GoodsListAdapter(this, mKeyword));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);


        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(30000);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);

        searchView.setQueryHint("请输入关键字");
        SearchView.SearchAutoComplete hint = searchView.findViewById(R.id.search_src_text);
        hint.setHintTextColor(Color.WHITE);
        hint.setTextColor(Color.WHITE);
        return super.onCreateOptionsMenu(menu);
    }
}
