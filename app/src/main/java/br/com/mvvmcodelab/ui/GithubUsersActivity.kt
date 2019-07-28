package br.com.mvvmcodelab.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import br.com.mvvmcodelab.R
import br.com.mvvmcodelab.di.GithubViewModelFactory
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class GithubUsersActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: GithubViewModelFactory

    private lateinit var viewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.fetchUsers()
        }

        subscribeToUsers()
        subscribeToErrors()
    }

    private fun subscribeToUsers() {
        val users = Observer<List<User>> { userList ->
            if (userList != null) {
                info.text = "Numero de elementos: ${userList.size}"
            }
        }

        viewModel.getUsers().observe(this, users)
    }

    private fun subscribeToErrors() {
        val errorMsg = Observer<Throwable> {error ->
            Log.d("Claudio - ", "error")
        }

        viewModel.getError().observe(this, errorMsg)

    }
}
