package br.com.mvvmcodelab.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { provideUrl() }
    single { provideGson() }
    single { provideOkhttpClient() }
    single { provideRetrofit(get(), get(), get())}
    single { provideGithubApi(get())}
}

fun provideUrl() = "https://api.github.com/"

fun provideGson() = GsonBuilder().create()

fun provideOkhttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    val builder = OkHttpClient.Builder()
        .readTimeout(45, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .connectTimeout(45, TimeUnit.SECONDS)

    return builder.build()
}

fun provideRetrofit(baseURL: String, client: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)
