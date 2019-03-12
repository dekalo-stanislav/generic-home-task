package ua.com.dekalo.hometask.domain

import ua.com.dekalo.hometask.models.Country

interface CountriesRepository: GenericRepository<List<Country>, GenericSpec>