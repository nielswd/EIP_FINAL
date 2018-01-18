package com.example.vincent.eip.Network.goldenbook;

import com.example.vincent.eip.Network.infos.infoevent.InfoEvents;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by iNfecteD on 08/06/2017.
 */

public interface RetrofitInterfaceGoldenBook {
    @Headers( "Content-Type: application/json" )
    @PUT("client/goldenbook")
    Call<GoldenBook> getCommentsBook(@Body RequestBody userClientInfo);

    @Headers( "Content-Type: application/json" )
    @POST("client/goldenbook/create")
    Call<GoldenBook> createCommentBook(@Body RequestBody userClientInfo);

}
