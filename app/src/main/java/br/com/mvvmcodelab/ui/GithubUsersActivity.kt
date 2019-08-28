package br.com.mvvmcodelab.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import br.com.mvvmcodelab.R
import br.com.mvvmcodelab.model.User
import br.com.mvvmcodelab.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_github_user.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class GithubUsersActivity : AppCompatActivity() {

    private val viewModel: GithubViewModel by viewModel()
    private val adapter: GithubUserAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_user)

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
