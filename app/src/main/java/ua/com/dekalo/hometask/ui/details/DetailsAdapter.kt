package ua.com.dekalo.hometask.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.models.CountryDetails
import ua.com.dekalo.hometask.ui.utils.AdapterUtils

open class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view)

class CountryPreviewViewHolder(view: View) : DetailsViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): CountryPreviewViewHolder {
            return CountryPreviewViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.details_preview_item_layout,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(country: Country) {
        itemView.findViewById<TextView>(R.id.details_preview_country_name).text = country.name
        itemView.findViewById<TextView>(R.id.details_preview_local_country_name).text = country.nativeName
        itemView.findViewById<TextView>(R.id.details_preview_population).text = country.population.toString()
    }
}

class CountryDetailsViewHolder(view: View) : DetailsViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup): CountryDetailsViewHolder {
            return CountryDetailsViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.details_item_layout,
                    parent,
                    false
                )
            )
        }
    }

    fun bind(countryDetails: CountryDetails) {
        itemView.findViewById<TextView>(R.id.details_country_name).text = countryDetails.name
        itemView.findViewById<TextView>(R.id.details_local_country_name).text = countryDetails.nativeName
        itemView.findViewById<TextView>(R.id.details_population).text = countryDetails.population.toString()
    }
}

class DetailsAdapter : RecyclerView.Adapter<DetailsViewHolder>() {

    private var items = listOf<DetailsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        return when (DetailsItem::class.sealedSubclasses[viewType]) {
            CountryDetailsItem::class -> CountryDetailsViewHolder.create(parent)
            CountryPreviewItem::class -> CountryPreviewViewHolder.create(parent)
            else -> throw IllegalStateException("Unknown viewType = $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return DetailsItem::class.sealedSubclasses.indexOf(items[position]::class)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        when (holder) {
            is CountryDetailsViewHolder -> holder.bind((items[position] as CountryDetailsItem).details)
            is CountryPreviewViewHolder -> holder.bind((items[position] as CountryPreviewItem).country)
            else -> Assertions.fail { IllegalStateException() }
        }
    }

    fun updateItems(newItems: List<DetailsItem>) {
        val oldItems = items
        items = newItems
        AdapterUtils.notifyChanges(this, oldItems, items)
    }
}