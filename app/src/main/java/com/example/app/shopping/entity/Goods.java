package com.example.app.shopping.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Goods {
    @Id
    private long id = 0;
    private String name;
    private String pic;
    private double price;
    private String dept;
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", dept='" + dept + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
