package br.com.mvvmcodelab.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import br.com.mvvmcodelab.ui.GithubUsersActivity
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideGithubViewModel(activity: GithubUsersActivity): ViewModel = ViewModelProviders.of(activity).get(GithubViewModel::class.java)

}