package ua.com.dekalo.hometask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post

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
        itemView.setOnClickListener { onClick() }
    }
}

class MainActivityAdapter(private val onItemClick: (Int, Post) -> Unit) :
    RecyclerView.Adapter<MainActivityAdapterItemViewHolder>() {

    private var items = listOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterItemViewHolder {
        return MainActivityAdapterItemViewHolder.create(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainActivityAdapterItemViewHolder, position: Int) {
        holder.bind(items.get(position)) { onItemClick(position, items[position]) }
    }

    fun updateContent(newItems: List<Post>) {
        val oldItems = items
        items = newItems
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun getOldListSize() = oldItems.size
            override fun getNewListSize() = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

        }).dispatchUpdatesTo(this)
    }
}