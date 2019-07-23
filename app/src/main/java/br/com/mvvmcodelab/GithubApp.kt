package br.com.mvvmcodelab

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import br.com.mvvmcodelab.di.DaggerAppComponent


class GithubApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}