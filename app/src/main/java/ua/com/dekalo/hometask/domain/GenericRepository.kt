package ua.com.dekalo.hometask.domain

import io.reactivex.Observable

interface GenericRepository<T, S : GenericSpec> {

    companion object {
        fun createGenericSpec(allowCache: Boolean = true, loadFresh: Boolean = true): GenericSpec {
            return GenericSpec(allowCache, loadFresh)
        }
    }

    fun load(spec: S): Observable<T>
}