package br.com.mvvmcodelab.di

import br.com.mvvmcodelab.viewmodel.GithubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GithubViewModel(get()) }
}



/*@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GithubViewModel::class)
    abstract fun bindGithubViewModel(viewModel: GithubViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}*/

