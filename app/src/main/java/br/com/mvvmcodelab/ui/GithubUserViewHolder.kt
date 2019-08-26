package br.com.mvvmcodelab.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.mvvmcodelab.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cell_github.view.*

class GithubUserViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bindView(user: User) {
        view.user_name.text = user.login
        Picasso.with(view.context).load(user.avatar_url).into(view.user_image)
    }
}