package com.example.app.shopping.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Order {
    public static final int ORDERED = 0;
    public static final int NOT_ORDERED = 1;

    @Id
    private long id = 0;
    private long userId;
    private long goodsId;
    private int count;
    private int status;
    private String date;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", status=" + status +
                ", date='" + date + '\'' +
                "}\n";
    }
}
