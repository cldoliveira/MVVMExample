package br.com.mvvmcodelab.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.mvvmcodelab.R
import br.com.mvvmcodelab.model.User
import javax.inject.Inject

class GithubUserAdapter @Inject constructor(): RecyclerView.Adapter<GithubUserViewHolder>() {

    private var users = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): GithubUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell_github, parent, false)
        return GithubUserViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.bindView(users[position])
    }

    fun updateUsers(newUsers: List<User>?) {
        if (newUsers != null) {
            users = newUsers
            notifyDataSetChanged()
        }
    }
}