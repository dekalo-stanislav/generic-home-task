package ua.com.dekalo.hometask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.AdapterUtils

class MainActivityAdapterItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): MainActivityAdapterItemViewHolder {
            return MainActivityAdapterItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.main_activity_recycler_item,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(post: Post, onClick: () -> Unit) {
        itemView.findViewById<TextView>(R.id.post_title_text_view).text = post.title
        itemView.findViewById<TextView>(R.id.post_author_text_view).text = post.author

        if (post.commentsCount > 0) {
            itemView.findViewById<TextView>(R.id.post_comments_count_text_view).text =
                itemView.context.getString(R.string.main_screen_comments_count, post.commentsCount)
            itemView.setOnClickListener { onClick() }
        } else {
            itemView.findViewById<TextView>(R.id.post_comments_count_text_view).text =
                itemView.context.getString(R.string.main_screen_no_comments)
            itemView.setOnClickListener(null)
        }
    }
}

class MainActivityAdapter(private val onItemClick: (Int, Post, View) -> Unit) :
    RecyclerView.Adapter<MainActivityAdapterItemViewHolder>() {

    private var items = listOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterItemViewHolder {
        return MainActivityAdapterItemViewHolder.create(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainActivityAdapterItemViewHolder, position: Int) {
        holder.bind(items.get(position)) { onItemClick(position, items[position], holder.itemView) }
    }

    fun updateContent(newItems: List<Post>) {
        val oldItems = items
        items = newItems
        AdapterUtils.notifyChanges(this, oldItems, items)
    }
}