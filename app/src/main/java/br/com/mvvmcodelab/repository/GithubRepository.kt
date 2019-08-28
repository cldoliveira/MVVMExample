package br.com.mvvmcodelab.repository

import br.com.mvvmcodelab.di.GithubApi
import br.com.mvvmcodelab.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GithubRepository(private val api: GithubApi) {

    fun getUsers(): Single<List<User>> {
        return api.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}