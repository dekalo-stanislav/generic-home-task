package ua.com.dekalo.hometask.cache

interface KeyBasedCache<K, V> {
    fun put(key: K, value: V)
    fun get(key: K): V?
    fun isEmpty(key: K): Boolean
    fun clear(key: K)
    fun clear()
}