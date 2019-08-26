package br.com.mvvmcodelab.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.repository.GithubRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GithubViewModel @Inject constructor(private val repository: GithubRepository): ViewModel() {
    private val disposable = CompositeDisposable()
    private val users = MutableLiveData<List<User>>()
    private val usersFiltered = MutableLiveData<List<User>>()
    private val errorMsg = MutableLiveData<Throwable>()

    fun fetchUsers() {
        disposable.add(repository.getUsers()
            .subscribe({userList -> users.value = userList},
                {error -> errorMsg.value = error}))
    }

    fun getUsers() = users

    fun getError() = errorMsg

    fun getUsersFiltered() = usersFiltered

    fun filterUsers(key: String) {
        usersFiltered.value = users.value?.filter { it.login.contains(key, true)}
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}