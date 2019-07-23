package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface GithubApi {

    @GET("users")
    fun getUsers(): Single<List<User>>

}