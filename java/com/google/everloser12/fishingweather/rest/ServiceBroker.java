package com.google.everloser12.fishingweather.rest;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.everloser12.fishingweather.constants.Constants;
import com.google.everloser12.fishingweather.model.Root;
import com.google.everloser12.fishingweather.prework.WeatherRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by al-ev on 29.04.2016.
 */
public class ServiceBroker {
    private static ServiceBroker ourInstance = new ServiceBroker();
    private Retrofit mRetrofit;
    private Root root;


    public static ServiceBroker getInstance() {
        return ourInstance;
    }

    private ServiceBroker() {
    }


    public Retrofit getRetrofit() {
        if (mRetrofit == null) {

            OkHttpClient client = new OkHttpClient.Builder()

                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Headers.Builder builder = request.headers().newBuilder();
                            request = request.newBuilder().headers(builder.build()).build();

                            return chain.proceed(request);
                        }
                    })
                    .build();

            GsonBuilder gsonBuilder = new GsonBuilder();

            //gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

            Gson gson = gsonBuilder.create();

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }
        return mRetrofit;
    }

    public Root getRoot() {
        return root;
    }

    public void getWeather(WeatherRequest weatherRequest, final CallBack callBack) {
        IOWApi iowApi = getRetrofit().create(IOWApi.class);
        Log.d("Moi", "create r ");

        Call<Root> call = iowApi.getWeather(weatherRequest.getLat(), weatherRequest.getLon(),
                Constants.DAY_CNT, Constants.API_KEY);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                root = response.body();

                if (response.isSuccessful() && root != null) {
                    //ok
                    Log.d("Moi", "all ok, ");
                    callBack.response(false);
                } else {
                    //bad
                    try {
                        Log.d("Moi", "error response body = " + response.errorBody().string());
                    } catch (IOException e) {

                    }
                    callBack.response(true);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                // нет интернета, ошибка в коде
                Log.d("Moi", "нет интернета, ошибка в коде" + t.getMessage());
                callBack.response(true);
            }
        });
    }


}