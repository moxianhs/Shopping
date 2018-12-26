package com.example.app.shopping.goods;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.Goods_;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    private AppCompatActivity mActivity;
    private List<Goods> mGoodsList;
    private static final String TAG = "GoodsListAdapter";

    public GoodsListAdapter(AppCompatActivity activity, String keyword) {
        this.mActivity = activity;

        Box<Goods> goodsBox = ShoppingApplication.boxStore.boxFor(Goods.class);
        // TODO test keyword
        Log.e(TAG, "GoodsListAdapter: " + keyword);
        Query<Goods> goodsQuery = goodsBox.query()
                .equal(Goods_.dept, keyword)
                .build();

        mGoodsList = goodsQuery.find();
        Log.e(TAG, "GoodsListAdapter: " + mGoodsList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.item_goods_list, viewGroup, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Goods goods = mGoodsList.get(i);
        viewHolder.name.setText(goods.getName());
        viewHolder.price.setText("￥" + goods.getPrice());

        MutableLiveData<byte[]> byteArray = new MutableLiveData<>();

        byteArray.observe(mActivity, bytes -> {
            assert bytes != null;
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            viewHolder.pic.setImageBitmap(bitmap);
        });

        loadPic(byteArray, i);

    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }


    private void loadPic(MutableLiveData<byte[]> byteArray, int i) {
        AsyncTask.execute(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("http:" + mGoodsList.get(i).getPic()).openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                byte[] result = new byte[inputStream.available()];
                int len = inputStream.read(result, 0, result.length);
                Log.d(TAG, "loadPic: " + (len == result.length));

                byteArray.postValue(result);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView pic;
        TextView name;
        TextView price;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
