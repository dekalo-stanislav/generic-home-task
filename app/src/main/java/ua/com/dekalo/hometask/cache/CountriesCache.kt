package ua.com.dekalo.hometask.cache

import ua.com.dekalo.hometask.models.Country
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountriesCache @Inject constructor() : InMemoryCache<List<Country>>()