package ua.com.dekalo.hometask.domain

import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun providesCountryRepository(defaultCountriesRepository: DefaultCountriesRepository): CountriesRepository {
        return defaultCountriesRepository
    }

    @Provides
    fun providesDetailsRepository(defaultCountryDetailsRepository: DefaultCountryDetailsRepository): CountryDetailsRepository {
        return defaultCountryDetailsRepository
    }
}