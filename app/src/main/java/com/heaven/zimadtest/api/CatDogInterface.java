package com.heaven.zimadtest.api;

import com.heaven.zimadtest.utils.Constants;
import com.heaven.zimadtest.model.CatDog;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatDogInterface {

    @GET("xim/api.php")
    Call<CatDog> getAnimals(@Query("query") String query);

    class Creator {
        public static CatDogInterface getRetrofitClient() {
            Retrofit.Builder builder;
            Retrofit retrofit;

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            builder =
                    new Retrofit.Builder()
                            .baseUrl(Constants.CAT_DOG_ENDPOINT)
                            .addConverterFactory(
                                    GsonConverterFactory.create()
                            );

            retrofit =
                    builder
                            .client(
                                    httpClient.build()
                            )
                            .build();

            return retrofit.create(CatDogInterface.class);
        }
    }

}
