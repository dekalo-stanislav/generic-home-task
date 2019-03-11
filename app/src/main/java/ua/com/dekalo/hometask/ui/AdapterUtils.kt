package ua.com.dekalo.hometask.ui

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class AdapterUtils {

    companion object {
        fun <T, P : RecyclerView.ViewHolder> notifyChanges(
            adapter: RecyclerView.Adapter<P>,
            oldItems: List<T>,
            newItems: List<T>
        ) {
            DiffUtil.calculateDiff(object : DiffUtil.Callback() {

                override fun getOldListSize() = oldItems.size
                override fun getNewListSize() = newItems.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldItems[oldItemPosition] == newItems[newItemPosition]

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldItems[oldItemPosition] == newItems[newItemPosition]

            }).dispatchUpdatesTo(adapter)
        }
    }
}