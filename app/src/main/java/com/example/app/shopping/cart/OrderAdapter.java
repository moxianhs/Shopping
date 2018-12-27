package com.example.app.shopping.cart;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d.lib.slidelayout.SlideLayout;
import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.Order;
import com.example.app.shopping.entity.Order_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private static final String TAG = "OrderAdapter";
    private List<Order> orders;

    OrderAdapter() {
        Box<Order> orderBox = ShoppingApplication.boxStore.boxFor(Order.class);
        Query<Order> query = orderBox.query()
                .equal(Order_.userId, ShoppingApplication.currentUserId)
                .equal(Order_.status, Order.ORDERED)
                .build();
        orders = query.find();
        Log.e(TAG, "OrderAdapter: " + orders);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Order order = orders.get(position);
        Goods goods = ShoppingApplication.boxStore.boxFor(Goods.class).get(order.getGoodsId());
        if (goods != null) {
            holder.name.setText(goods.getName());
            holder.price.setText("￥" + goods.getPrice());
            Glide.with(holder.itemView)
                    .load(goods.getPic())
                    .into(holder.pic);
            holder.total.setText(String.valueOf(goods.getPrice() * order.getCount()));
            holder.date.setText(order.getDate());
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView pic;
        TextView name;
        TextView price;
        TextView total;
        TextView date;
        SlideLayout slider;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            slider = itemView.findViewById(R.id.slider);

            pic = slider.findViewById(R.id.pic);
            name = slider.findViewById(R.id.name);
            price = slider.findViewById(R.id.price);
            total = slider.findViewById(R.id.total);
            date = slider.findViewById(R.id.date);
        }
    }
}
