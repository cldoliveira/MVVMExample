package br.com.mvvmcodelab

import android.app.Application
import br.com.mvvmcodelab.di.adapterModule

import br.com.mvvmcodelab.di.repositoryModule
import br.com.mvvmcodelab.di.retrofitModule
import br.com.mvvmcodelab.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GithubApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubApp)
            modules(listOf(retrofitModule, repositoryModule, viewModelModule, adapterModule))
        }

    }

    /*override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }*/
}