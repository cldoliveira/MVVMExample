package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.ui.GithubUsersActivity
import dagger.Binds
import dagger.Module

@Module
abstract class GithubUserModule {

    @Binds
    internal abstract fun GithubActivity(activity: GithubUsersActivity): GithubUsersActivity

}