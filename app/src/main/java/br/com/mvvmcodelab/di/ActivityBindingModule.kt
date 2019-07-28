package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.ui.GithubUsersActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun githubActivity(): GithubUsersActivity

}