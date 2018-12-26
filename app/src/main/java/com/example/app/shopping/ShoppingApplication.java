package com.example.app.shopping;

import android.app.Application;

import com.example.app.shopping.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class ShoppingApplication extends Application {

    public static ShoppingApplication application;
    public static BoxStore boxStore;
    public static long currentUserId = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        boxStore = MyObjectBox.builder().androidContext(this).build();
    }
}
