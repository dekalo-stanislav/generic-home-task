package ua.com.dekalo.hometask.domain

import ua.com.dekalo.hometask.models.CountryDetails

class CountryDetailSpec(val name: String, allowCache: Boolean = true, loadFresh: Boolean = true) :
    GenericSpec(allowCache, loadFresh)

interface CountryDetailsRepository : GenericRepository<CountryDetails, CountryDetailSpec> {

    companion object {
        fun createCountryDetailSpec(
            name: String,
            allowCache: Boolean = true,
            loadFresh: Boolean = true
        ): CountryDetailSpec {
            return CountryDetailSpec(name, allowCache, loadFresh)
        }
    }
}