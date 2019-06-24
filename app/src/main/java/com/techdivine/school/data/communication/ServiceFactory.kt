package com.techdivine.school.data.communication

import android.util.Log
import com.techdivine.school.App
import com.techdivine.school.BuildConfig
import com.techdivine.school.common.constants.BASE_URL
import com.techdivine.school.common.utils.AppsUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ServiceFactory {

    fun provideApi(defaultHeader:Boolean): ApiService {
        if(defaultHeader) {
            return provideRetrofitWithHeader(BASE_URL).create(ApiService::class.java)
         }
        else{
            return provideRetrofit(BASE_URL).create(ApiService::class.java)
        }
    }

     private fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideRetrofitWithHeader(baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClientWithHeader())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .connectTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    private fun provideOkHttpClientWithHeader(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .addInterceptor(headerAuthorizationInterceptor)
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> Log.d("Injector", message) })
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor
    }

    var headerAuthorizationInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request = chain.request()
            var token:String?
            token = null
            //token= AppsUtil.getUserToken(App.instance?.applicationContext)
            if(token==null)
                token=""
            val headers = request.headers().newBuilder()
                    .add("Authorization", "Basic Og==")
                    .add("Content-Type", "application/json")
                    .add("Accept", "application/json")
                    .add("token", token).
                    build()
            request = request.newBuilder().headers(headers).build()
            return chain.proceed(request)
        }
    }
}