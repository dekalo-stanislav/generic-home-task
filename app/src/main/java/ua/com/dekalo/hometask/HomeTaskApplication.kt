package ua.com.dekalo.hometask

import android.app.Application
import android.util.Log
import com.heershingenmosiken.assertions.Assertions
import com.heershingenmosiken.assertions.android.AndroidAssertions

class HomeTaskApplication : Application() {

    companion object {
        private const val LOG_TAG = "HomeTaskApplication"
    }

    override fun onCreate() {

        super.onCreate()

        if (BuildConfig.DEBUG) {
            AndroidAssertions.shouldCrashOnAssertion(true)
        }

        Assertions.addAssertionHandler {
            Log.d(LOG_TAG, "assertion happens: ${it.message} silent = ${it.silent}", it.throwable)
        }
    }

}