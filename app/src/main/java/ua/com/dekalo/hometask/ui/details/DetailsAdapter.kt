package ua.com.dekalo.hometask.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.ui.AdapterUtils

open class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view)
class PostViewHolder(view: View) : DetailsViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            return PostViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.details_post_layout,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(postDetailsItem: PostDetailsItem) {
        itemView.findViewById<TextView>(R.id.post_title_text_view).text = postDetailsItem.post.title
        itemView.findViewById<TextView>(R.id.post_author_text_view).text = postDetailsItem.post.author
    }
}

class CommentViewHolder(view: View) : DetailsViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): CommentViewHolder {
            return CommentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.details_comment_layout,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(commentDetailsItem: CommentDetailsItem) {
        itemView.findViewById<TextView>(R.id.comment_author_text_view).text = commentDetailsItem.comment.author
        itemView.findViewById<TextView>(R.id.comment_content_text_view).text = commentDetailsItem.comment.content
    }
}

class DetailsAdapter : RecyclerView.Adapter<DetailsViewHolder>() {

    private var items = listOf<DetailsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return when (DetailsItem::class.sealedSubclasses[viewType]) {
            PostDetailsItem::class -> PostViewHolder.create(parent)
            CommentDetailsItem::class -> CommentViewHolder.create(parent)
            else -> throw IllegalStateException("Unknown viewType = $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return DetailsItem::class.sealedSubclasses.indexOf(items[position]::class)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(items[position] as PostDetailsItem)
            is CommentViewHolder -> holder.bind(items[position] as CommentDetailsItem)
            else -> Assertions.fail { IllegalStateException() }
        }
    }

    fun updateItems(newItems: List<DetailsItem>) {
        val oldItems = items
        items = newItems
        AdapterUtils.notifyChanges(this, oldItems, items)
    }
}