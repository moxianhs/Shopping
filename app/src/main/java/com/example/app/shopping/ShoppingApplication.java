package com.example.app.shopping;

import android.app.Application;
import android.util.Log;

import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.MyObjectBox;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.objectbox.Box;
import io.objectbox.BoxStore;


public class ShoppingApplication extends Application {
    private static final String TAG = "ShoppingApplication";
    public static ShoppingApplication application;
    public static BoxStore boxStore;
    public static long currentUserId = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        boxStore = MyObjectBox.builder().androidContext(this).build();

        try {
            goodsInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goodsInit() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("goods.txt")));
        String line = br.readLine();
        Gson gson = new Gson();
        Box<Goods> goodsBox = boxStore.boxFor(Goods.class);
        goodsBox.removeAll();
        while (line != null && line.length() > 0) {
            Goods goods = gson.fromJson(line, Goods.class);
            Log.e(TAG, "goodsInit: " + goods);
            line = br.readLine();
            goodsBox.put(goods);
        }

        br.close();
    }
}
