package br.com.mvvmcodelab.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideUrl() = "https://api.github.com/"


    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .connectTimeout(45, TimeUnit.SECONDS)

        return builder.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(baseURL: String, client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

}