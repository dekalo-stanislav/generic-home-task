package ua.com.dekalo.hometask.cache

open class InMemoryCache<T> : Cache<T> {

    @Volatile
    private var value: T? = null

    override fun get(): T? = value
    override fun isEmpty() = value == null

    override fun put(value: T) {
        this.value = value
    }

    override fun clear() {
        this.value = null
    }
}