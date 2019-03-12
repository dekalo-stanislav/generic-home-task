package ua.com.dekalo.hometask.ui.utils

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.heershingenmosiken.assertions.Assertions

interface NonnullLiveData<T> {
    fun getValue(): T
    fun observe(owner: LifecycleOwner, observer: (T) -> Unit)
    fun asLiveData(): LiveData<T>
}

class NonnullMutableLiveData<T>(default: T) : LiveData<T>(), NonnullLiveData<T> {
    init {
        Assertions.assertTrue(Looper.getMainLooper() == Looper.myLooper()) { IllegalStateException("Should be constructed on main thread only") }
        if (default == null) throw IllegalStateException("NonnullMutableLiveData not supports null")
        value = default
    }

    public override fun setValue(value: T) {
        if (value == null) throw IllegalStateException("NonnullMutableLiveData not supports null")
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        if (value == null) throw IllegalStateException("NonnullMutableLiveData not supports null")
        super.postValue(value)
    }

    override fun getValue(): T {
        return super.getValue()!!
    }

    override fun observe(owner: LifecycleOwner, observer: (T) -> Unit) {
        super.observe(owner, Observer { t -> observer(t!!) })
    }

    override fun asLiveData(): LiveData<T> = this
}