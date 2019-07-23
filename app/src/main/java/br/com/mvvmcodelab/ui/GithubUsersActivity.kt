package br.com.mvvmcodelab.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import br.com.mvvmcodelab.R
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class GithubUsersActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.fetchUsers()

        subscribeToUsers()
        subscribeToErrors()
    }

    private fun subscribeToUsers() {
        val users = Observer<List<User>> { userList ->
            Log.d("Claudio - ", "give a list of user...")
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
