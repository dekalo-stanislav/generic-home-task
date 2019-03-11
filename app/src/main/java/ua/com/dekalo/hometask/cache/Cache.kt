package ua.com.dekalo.hometask.cache

interface Cache<T> {
    fun put(value: T)
    fun get(): T?
    fun isEmpty(): Boolean
    fun clear()
}