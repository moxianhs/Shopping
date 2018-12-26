package com.example.app.shopping.goods;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoodsPicService {

    @GET()
    Call<Byte[]> getPic();
}
