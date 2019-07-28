package br.com.mvvmcodelab.di

import android.app.Application
import br.com.mvvmcodelab.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
    ActivityBindingModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<GithubApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}