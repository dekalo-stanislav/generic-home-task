package ua.com.dekalo.hometask.ui.main

import ua.com.dekalo.hometask.models.Country

data class MainActivityData(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)