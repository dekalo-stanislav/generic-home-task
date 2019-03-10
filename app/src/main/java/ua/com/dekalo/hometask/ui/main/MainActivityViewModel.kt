package ua.com.dekalo.hometask.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.com.dekalo.hometask.model.DataModel

class MainActivityViewModel : ViewModel() {

    private val _data = MutableLiveData<DataModel>()
    val data: LiveData<DataModel> get() = _data

    fun loadData() {
        _data.postValue(DataModel(listOf("Hi", "Hey", "Bye")))
    }
}
