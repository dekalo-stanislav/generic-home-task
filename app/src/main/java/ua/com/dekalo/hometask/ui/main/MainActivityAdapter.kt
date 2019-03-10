package ua.com.dekalo.hometask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.com.dekalo.hometask.R

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

    fun bind(data: String, onClick: () -> Unit) {
        itemView.findViewById<TextView>(R.id.text_view).text = data
        itemView.setOnClickListener { onClick() }
    }
}

class MainActivityAdapter(private val onItemClick: (Int, String) -> Unit) :
    RecyclerView.Adapter<MainActivityAdapterItemViewHolder>() {

    private var items = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterItemViewHolder {
        return MainActivityAdapterItemViewHolder.create(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainActivityAdapterItemViewHolder, position: Int) {
        holder.bind(items.get(position)) { onItemClick(position, items[position]) }
    }

    fun updateContent(newItems: List<String>) {
        val oldItems = items
        items = newItems
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

            override fun getOldListSize() = oldItems.size
            override fun getNewListSize() = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }

        }).dispatchUpdatesTo(this)
    }
}