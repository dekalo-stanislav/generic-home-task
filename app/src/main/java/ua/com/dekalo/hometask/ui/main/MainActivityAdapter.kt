package ua.com.dekalo.hometask.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.domain.CountriesUtils
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.ui.utils.AdapterUtils
import ua.com.dekalo.hometask.ui.utils.svg.GlideSvg


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

    fun bind(country: Country, onClick: () -> Unit) {

        GlideSvg.with(itemView.context)
            .load(country.flag)
            .into(itemView.findViewById(R.id.main_flag_view))

        itemView.findViewById<TextView>(R.id.main_country_name).text =
            CountriesUtils.displayName(country.name, country.nativeName)

        itemView.findViewById<TextView>(R.id.main_population).text =
            CountriesUtils.humanReadablePopulation(country.population)

        itemView.setOnClickListener { onClick() }
    }
}

class MainActivityAdapter(private val onItemClick: (Int, Country, View) -> Unit) :
    RecyclerView.Adapter<MainActivityAdapterItemViewHolder>() {

    private var items = listOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityAdapterItemViewHolder {
        return MainActivityAdapterItemViewHolder.create(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainActivityAdapterItemViewHolder, position: Int) {
        holder.bind(items.get(position)) { onItemClick(position, items[position], holder.itemView) }
    }

    fun updateContent(newItems: List<Country>) {
        val oldItems = items
        items = newItems
        AdapterUtils.notifyChanges(this, oldItems, items)
    }
}