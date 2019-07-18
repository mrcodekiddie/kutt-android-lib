package com.kutt.it.kutt_android;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.kutt.it.kutt_android.Kutt.auth_api_key;
import static com.kutt.it.kutt_android.Kutt.domain;

public class ServiceGenerator
{
    private static final String baseURL=domain+"/api/url/";

    private static Integer timeOut=30;
    private static TimeUnit duration=TimeUnit.SECONDS;
    public static <S> S createService(Class<S> serviceClass)
    { HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient=new OkHttpClient.Builder()
                .connectTimeout(timeOut,duration)
                .readTimeout(timeOut,duration )
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .addHeader("X-API-Key",auth_api_key)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .writeTimeout(timeOut,duration)
                .build();




        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return  retrofit.create(serviceClass);
    }
}
