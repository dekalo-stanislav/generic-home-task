package ua.com.dekalo.hometask.domain

import com.heershingenmosiken.assertions.Assertions
import io.reactivex.Observable
import ua.com.dekalo.hometask.cache.CountriesCache
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.network.ServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCountriesRepository @Inject constructor(
    private val api: ServiceApi,
    private val countriesCache: CountriesCache
) :
    CountriesRepository {

    override fun load(spec: GenericSpec): Observable<List<Country>> {

        Assertions.assertTrue(spec.loadFresh || spec.allowCache) { IllegalStateException("Specification is empty") }

        var result: Observable<List<Country>> = Observable.empty()

        if (!countriesCache.isEmpty() && spec.allowCache) {
            countriesCache.get()?.let {
                result = result.concatWith(Observable.just(it))
            }
        }

        if (spec.loadFresh) {
            result = result.concatWith(api.getCountries()
                .doOnSuccess { countriesCache.put(it) })
        }

        return result
    }

}