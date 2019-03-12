package ua.com.dekalo.hometask.cache

import ua.com.dekalo.hometask.models.CountryDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryDetailsCache @Inject constructor() : InMemoryKeyBasedCache<String, CountryDetails>()