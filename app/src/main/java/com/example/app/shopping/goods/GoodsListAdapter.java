package com.example.app.shopping.goods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.Goods_;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    private String mKeyword;
    private Context mContext;
    private List<Goods> mGoodsList;

    public GoodsListAdapter(Context context, String keyword) {
        this.mKeyword = keyword;
        this.mContext = context;

        Box<Goods> goodsBox = ShoppingApplication.boxStore.boxFor(Goods.class);
        // TODO test keyword
        Query<Goods> goodsQuery = goodsBox.query()
                .equal(Goods_.dept, mKeyword)
                .build();

        mGoodsList = goodsQuery.find();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, viewGroup, false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Goods goods = mGoodsList.get(i);
        viewHolder.name.setText(goods.getName());
        viewHolder.price.setText("￥" + goods.getPrice());
        loadPic(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }


    private void loadPic(ViewHolder viewHolder, int i) {
        AsyncTask.execute(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("http:" + mGoodsList.get(i).getPic()).openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                byte[] bytes = new byte[65536];
                int len = is.read(bytes);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, len);
                viewHolder.pic.setImageBitmap(bitmap);

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
