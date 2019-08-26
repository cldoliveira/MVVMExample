package br.com.mvvmcodelab.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import br.com.mvvmcodelab.R
import br.com.mvvmcodelab.di.GithubViewModelFactory
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_github_user.*
import javax.inject.Inject

class GithubUsersActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: GithubViewModelFactory

    @Inject
    lateinit var adapter: GithubUserAdapter

    private lateinit var viewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GithubViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.fetchUsers()
        }

        progress_bar.visibility = View.VISIBLE

        initializeRecyclerView()
        subscribeToUsers()
        subscribeToErrors()
        observeUserListFiltered()
        addTextChangeListener()
    }

    private fun initializeRecyclerView() {
        rv_users.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_users.itemAnimator = DefaultItemAnimator()
        rv_users.adapter = adapter
    }

    private fun subscribeToUsers() {
        val users = Observer<List<User>> { userList ->
            progress_bar.visibility = View.GONE
            adapter.updateUsers(userList)
        }

        viewModel.getUsers().observe(this, users)
    }

    private fun subscribeToErrors() {
        val errorMsg = Observer<Throwable> {error ->
            progress_bar.visibility = View.GONE
            Toast.makeText(this, error?.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.getError().observe(this, errorMsg)
    }

    private fun observeUserListFiltered() {
        val userFiltered = Observer<List<User>> {userList ->
            adapter.updateUsers(userList)
        }

        viewModel.getUsersFiltered().observe(this, userFiltered)
    }

    private fun addTextChangeListener() {
        search_et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterUsers(s.toString())

            }
        })
    }
}
