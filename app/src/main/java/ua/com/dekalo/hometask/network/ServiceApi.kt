package ua.com.dekalo.hometask.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.models.CountryDetails

interface ServiceApi {

    @GET("all?fields=name;nativeName;flag;population")
    fun getCountries(): Single<List<Country>>

    @GET("name/{name}")
    fun getCountryDetails(@Path("name") name: String): Single<List<CountryDetails>>
}