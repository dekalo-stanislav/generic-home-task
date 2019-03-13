package ua.com.dekalo.hometask.models

import java.io.Serializable

data class Currency(
    val code: String,
    val name: String,
    val symbol: String
)

data class Language(val name: String, val nativeName: String)

data class CountryDetails(
    val id: Long,
    val name: String,
    val nativeName: String,
    val flag: String,
    val population: Long,
    val capital: String,
    val region: String,
    val subregion: String,
    val area: String,
    val gini: Double?,
    val currencies: List<Currency>,
    val languages: List<Language>
) : Serializable