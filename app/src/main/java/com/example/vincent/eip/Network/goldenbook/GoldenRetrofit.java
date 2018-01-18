package com.example.vincent.eip.Network.goldenbook;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.vincent.eip.Interfaces.GetMessagesCallback;
import com.example.vincent.eip.Interfaces.GoldenBookCallback;
import com.example.vincent.eip.Interfaces.SendMessageCallback;
import com.example.vincent.eip.Network.UserClientInfo;
import com.example.vincent.eip.Network.messages.ListMessages;
import com.example.vincent.eip.Network.messages.Messages;
import com.example.vincent.eip.Network.messages.MessagesInterface;
import com.example.vincent.eip.Network.messages.MessagesParams;
import com.example.vincent.eip.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iNfecteD on 08/06/2017.
 */

public class GoldenRetrofit {
    private Activity        activity;
    private GoldenBookCallback mListener;
    private GoldenBookCallback sendListener;

    public void login(final Context t,
                      final UserClientInfo clientInfo,
                      final GoldenBookCallback listener) {
        activity = (Activity) t;
        mListener= listener;
        tryLogin(tryCreateUserForLogin(clientInfo));
    }

    public void getSent(final Context t,
                      final UserClientInfo clientInfo,
                      final GoldenBookCallback listener) {
        activity = (Activity) t;
        mListener= listener;
        getSentMessages(tryCreateUserForLogin(clientInfo));
    }

    public void sendMessage(final Context t,
                            final GoldenBook clientInfo,
                            final GoldenBookCallback listener,
                            final UserMessages newMessage) {
        activity = (Activity) t;
        sendListener= listener;
        sendMessages(tryCreateJsonSendMessage(clientInfo));
    }


    private JSONObject tryCreateJsonSendMessage(GoldenBook book){


        Gson gson = new Gson();
        String jsonString = gson.toJson(book);

        try {
            JSONObject request = new JSONObject(jsonString);
            Log.d("json login", request.toString());
            return request;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject tryCreateUserForLogin(UserClientInfo clientInfo){
        MessagesParams params = new MessagesParams();
        params.setUser(clientInfo);

        Gson gson = new Gson();
        String jsonString = gson.toJson(params);

        try {
            JSONObject request = new JSONObject(jsonString);
            Log.d("json login", request.toString());
            return request;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sendMessages(JSONObject jsonLogin){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        //TODO Change level of Logging before Production! (go NONE)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(activity.getString(R.string.serverURLRest))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RetrofitInterfaceGoldenBook service = retrofit.create(RetrofitInterfaceGoldenBook.class);

        Call<GoldenBook> call = service.createCommentBook(createRequestBody(jsonLogin));
        call.enqueue(new Callback<GoldenBook>() {
            @Override
            public void onResponse(Call<GoldenBook> call, retrofit2.Response<GoldenBook> response) {
                manageSuccessfulMessageSent(response);
            }

            @Override
            public void onFailure(Call<GoldenBook> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSentMessages(JSONObject jsonLogin){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        //TODO Change level of Logging before Production! (go NONE)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(activity.getString(R.string.serverURLRest))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RetrofitInterfaceGoldenBook service = retrofit.create(RetrofitInterfaceGoldenBook.class);

        Call<GoldenBook> call = service.createCommentBook(createRequestBody(jsonLogin));
        call.enqueue(new Callback<GoldenBook>() {
            @Override
            public void onResponse(Call<GoldenBook> call, retrofit2.Response<GoldenBook> response) {
                manageSuccessfulLogin(response);
            }

            @Override
            public void onFailure(Call<GoldenBook> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void tryLogin(JSONObject jsonLogin){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        //TODO Change level of Logging before Production! (go NONE)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(activity.getString(R.string.serverURLRest))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RetrofitInterfaceGoldenBook service = retrofit.create(RetrofitInterfaceGoldenBook.class);

        Call<GoldenBook> call = service.getCommentsBook(createRequestBody(jsonLogin));
        call.enqueue(new Callback<GoldenBook>() {
            @Override
            public void onResponse(Call<GoldenBook> call, retrofit2.Response<GoldenBook> response) {
                manageSuccessfulLogin(response);
            }

            @Override
            public void onFailure(Call<GoldenBook> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public RequestBody createRequestBody(JSONObject jsonLogin){
        RequestBody myreqbody = null;
        try {
            myreqbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (new JSONObject(String.valueOf(jsonLogin))).toString());
            return myreqbody;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //TODO catch errors
       return null;
    }

    private void manageSuccessfulLogin(retrofit2.Response<GoldenBook> response) {
        Log.d("response messages", response.toString());
        mListener.getCommentsBook(response.body());
    }

    private void manageSuccessfulMessageSent(retrofit2.Response<GoldenBook> response) {
        Log.d("response messages", response.toString());
        sendListener.getCommentsBook(response.body());
    }
}
