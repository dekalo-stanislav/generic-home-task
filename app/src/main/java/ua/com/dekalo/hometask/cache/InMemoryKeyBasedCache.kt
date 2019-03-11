package ua.com.dekalo.hometask.cache

import java.util.*

open class InMemoryKeyBasedCache<K, V> : KeyBasedCache<K, V> {

    private val hashMap = Hashtable<K, V>()

    override fun isEmpty(key: K) = hashMap.contains(key)
    override fun get(key: K) = hashMap[key]

    override fun put(key: K, value: V) {
        hashMap[key] = value
    }

    override fun clear(key: K) {
        hashMap.remove(key)
    }

    override fun clear() {
        hashMap.clear()
    }
}