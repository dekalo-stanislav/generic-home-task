package ua.com.dekalo.hometask.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.domain.CountriesUtils
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.models.CountryDetails
import ua.com.dekalo.hometask.ui.utils.AdapterUtils
import ua.com.dekalo.hometask.ui.utils.svg.GlideSvg

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
        GlideSvg.with(itemView.context).load(country.flag).into(itemView.findViewById(R.id.details_preview_flag_view))

        itemView.findViewById<TextView>(R.id.details_preview_country_name).text =
            CountriesUtils.displayName(country.name, country.nativeName)

        itemView.findViewById<TextView>(R.id.details_preview_population).text =
            CountriesUtils.humanReadablePopulation(country.population)
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
        val context = itemView.context
        itemView.apply {
            findViewById<TextView>(R.id.details_capital_text_view).text =
                context.getString(R.string.country_capital_details_string, countryDetails.capital)

            findViewById<TextView>(R.id.details_region_subregion_text_view).text =
                context.getString(
                    R.string.country_region_subregion_details_string,
                    countryDetails.subregion,
                    countryDetails.region
                )

            findViewById<TextView>(R.id.details_area_text_view).text =
                context.getString(R.string.area_details_text_view, countryDetails.area)

            findViewById<TextView>(R.id.details_currencies_text_view).text =
                context.getString(
                    R.string.currecncies_details_string,
                    countryDetails.currencies.joinToString(separator = ", ") { "${it.symbol} (${it.name})" }
                )

            if (countryDetails.gini != null) {
                findViewById<TextView>(R.id.details_gini_index_text_view)?.let {
                    it.visibility = View.VISIBLE
                    it.text = context.getString(R.string.gini_index_details_string, countryDetails.gini)
                }
            } else {
                findViewById<TextView>(R.id.details_gini_index_text_view).visibility = View.GONE
            }

            findViewById<TextView>(R.id.details_languages_text_view).text =
                context.getString(
                    R.string.languages_details_string,
                    countryDetails.languages.joinToString(separator = ", ") { "${it.name} (${it.nativeName})" }
                )
        }
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