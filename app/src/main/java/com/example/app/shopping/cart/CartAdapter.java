package com.example.app.shopping.cart;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app.shopping.R;
import com.example.app.shopping.ShoppingApplication;
import com.example.app.shopping.entity.Goods;
import com.example.app.shopping.entity.Order;
import com.example.app.shopping.entity.Order_;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Order> orders;
    private Activity activity;

    CartAdapter(Activity activity) {
        this.activity = activity;
        Box<Order> orderBox = ShoppingApplication.boxStore.boxFor(Order.class);
        Query<Order> query = orderBox.query()
                .equal(Order_.userId, ShoppingApplication.currentUserId)
                .equal(Order_.status, Order.NOT_ORDERED)
                .build();
        orders = query.find();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

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
        }

        Button add = holder.itemView.findViewById(R.id.add);
        Button remove = holder.itemView.findViewById(R.id.remove);
        TextView count = holder.itemView.findViewById(R.id.count);

        MutableLiveData<Integer> sum = new MutableLiveData<>();
        sum.observe((LifecycleOwner) activity, v -> count.setText(String.valueOf(v)));
        sum.postValue(order.getCount());
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
    }

    @Override
    public int getItemCount() {
        return orders.size();
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
