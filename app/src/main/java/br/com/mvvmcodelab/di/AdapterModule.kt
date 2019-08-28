package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.ui.GithubUserAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { provideGithubUserAdapter() }
}

fun provideGithubUserAdapter() = GithubUserAdapter()