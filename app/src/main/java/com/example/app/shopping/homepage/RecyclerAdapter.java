package com.example.app.shopping.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app.shopping.R;
import com.example.app.shopping.goods.GoodsListActivity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] mItemTitles;
    private Context mContext;

    RecyclerAdapter(Context context) {
        mContext = context;
        mItemTitles = mContext.getResources().getStringArray(R.array.homepage_item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_recycler_homepage, viewGroup, false);
        v.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, GoodsListActivity.class);
            intent.putExtra("keyword", mItemTitles[i].replace("区", ""));
            mContext.startActivity(intent);
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(mItemTitles[i]);
    }

    @Override
    public int getItemCount() {
        return mItemTitles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
