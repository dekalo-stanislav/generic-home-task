package ua.com.dekalo.hometask.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.heershingenmosiken.assertions.Assertions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.com.dekalo.hometask.domain.CountryDetailsRepository
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.models.CountryDetails
import ua.com.dekalo.hometask.ui.utils.NonnullLiveData
import ua.com.dekalo.hometask.ui.utils.NonnullMutableLiveData
import javax.inject.Inject

sealed class DetailsItem
data class CountryPreviewItem(val country: Country) : DetailsItem()
data class CountryDetailsItem(val details: CountryDetails) : DetailsItem()

open class DetailsActivityViewModel @Inject constructor(private val countryDetailsRepository: CountryDetailsRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @Volatile
    private var _lastDetailsData = DetailsActivityData()
    private val _detailsData = NonnullMutableLiveData(_lastDetailsData)
    val detailsData: NonnullLiveData<DetailsActivityData> get() = _detailsData

    private var country: Country? = null
    private var countryDetails: CountryDetails? = null

    fun init(country: Country) {
        this.country = country
        changeViewModelState { it.copy(items = listOf<DetailsItem>(CountryPreviewItem(country))) }
    }

    fun load(allowCache: Boolean = true) {
        country?.let { country ->
            compositeDisposable.add(
                countryDetailsRepository
                    .load(CountryDetailsRepository.createCountryDetailSpec(country.name, allowCache = allowCache))
                    .doOnSubscribe { changeViewModelState { it.copy(isLoading = true) } }
                    .subscribeOn(Schedulers.io())
                    .subscribe({ countryDetails ->
                        this.countryDetails = countryDetails
                        changeViewModelState {
                            it.copy(items = listOf<DetailsItem>(CountryDetailsItem(countryDetails)), error = null)
                        }
                    }, { throwable ->
                        Log.d("test111", "failedToLoadContent()", throwable)
                        changeViewModelState { it.copy(isLoading = false, error = throwable) }
                    }, {
                        changeViewModelState { it.copy(isLoading = false) }
                    })
            )
        } ?: Assertions.fail { java.lang.IllegalStateException("Country is null") }

    }

    private fun changeViewModelState(change: (DetailsActivityData) -> DetailsActivityData) {
        _lastDetailsData = change(_lastDetailsData)
        _detailsData.postValue(_lastDetailsData)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}