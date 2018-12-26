package com.example.app.shopping.weather;

import com.google.gson.annotations.SerializedName;

public class City {
    private int id;
    private int pid;
    @SerializedName("city_name")
    private String name;
    @SerializedName("city_code")
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
