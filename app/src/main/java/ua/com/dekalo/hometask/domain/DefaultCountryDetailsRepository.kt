package ua.com.dekalo.hometask.domain

import com.heershingenmosiken.assertions.Assertions
import io.reactivex.Observable
import io.reactivex.Single
import ua.com.dekalo.hometask.cache.CountryDetailsCache
import ua.com.dekalo.hometask.models.CountryDetails
import ua.com.dekalo.hometask.network.ServiceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCountryDetailsRepository @Inject constructor(
    private val api: ServiceApi,
    private val countryDetailsCache: CountryDetailsCache
) : CountryDetailsRepository {
    override fun load(spec: CountryDetailSpec): Observable<CountryDetails> {
        Assertions.assertTrue(spec.loadFresh || spec.allowCache) { IllegalStateException("Specification is empty") }

        var result: Observable<CountryDetails> = Observable.empty()

        if (!countryDetailsCache.isEmpty(spec.name) && spec.allowCache) {
            countryDetailsCache.get(spec.name)?.let {
                result = result.concatWith(Observable.just(it))
            }
        }

        if (spec.loadFresh) {

            val networkObservable = api.getCountryDetails(spec.name)
                .flatMap {
                    if (it.size == 1) {
                        Single.just(it[0])
                    } else {
                        Single.error(IllegalStateException("Failed to load country details as size = ${it.size}"))
                    }
                }
                .doOnSuccess { countryDetailsCache.put(spec.name, it) }

            result = result.concatWith(networkObservable)
        }

        return result
    }
}