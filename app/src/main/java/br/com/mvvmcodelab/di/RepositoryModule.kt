package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.repository.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideGithubRepository(get()) }
}

fun provideGithubRepository(api: GithubApi) = GithubRepository(api)