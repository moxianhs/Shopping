package com.example.app.shopping.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.Goods_;

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
        viewHolder.itemView.setTag(goods.getId());
        Glide.with(mActivity)
                .load("http:" + goods.getPic())
                .into(viewHolder.pic);
        viewHolder.itemView.setOnClickListener(v -> {
            long id = (long) v.getTag();
            Intent intent = new Intent(mActivity, GoodsDetailActivity.class);
            intent.putExtra("ID", id);
            mActivity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
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
