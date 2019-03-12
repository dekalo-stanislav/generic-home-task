package ua.com.dekalo.hometask.models

import java.io.Serializable

data class CountryDetails(
    val id: Long,
    val name: String,
    val nativeName: String,
    val flag: String,
    val population: Long
) : Serializable