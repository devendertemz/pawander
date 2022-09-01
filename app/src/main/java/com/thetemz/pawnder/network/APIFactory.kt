package com.thetemz.pawnder.network

import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIFactory {

    private const val API_TIMEOUT: Long = 30
    private const val BASE_URL = "https://miraanrai.com/pet_application/api/"


    fun makeServiceAPi(): RetrofitService {
        return makeRetrofit(
            makeOkHttpClient(makeLoggingInterceptor()),
            makeGson()
        ).create(RetrofitService::class.java)
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val requestToApiInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url
                .newBuilder()
                // ALONE_API is just the "PUTAPIKEYHERE!!!"
                //  .addQueryParameter("api_key","PUTAPIKEYHERE!!!")
                //codeForActionOrComedy is an Int
                // .addQueryParameter("with_genres","$codeForActionOrComedy")
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url.toString().replace("%3D", "=")
                    .replace("%26", "&")
                   /* .replace("%3D", "=")
                    .replace("","")*/)
                .build()
            return@Interceptor chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            // .addInterceptor(headerInterceptor())
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    private fun headerInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
                    chain.request().newBuilder().addHeader("Connection", "close").build()
                return chain.proceed(request)
            }

        }
    }


    private fun makeRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(httpLogger)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private val httpLogger: HttpLoggingInterceptor.Logger by lazy {
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.println(
                    Log.VERBOSE,
                    "WEB SERVICE",
                    "RESPONSE VALUE $message"
                )
            }
        }
    }

}