package ua.com.dekalo.hometask

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.com.dekalo.hometask.cache.InMemoryCache

class InMemoryCacheTest {

    companion object {
        private const val SAMPLE_VALUE = "SAMPLE_VALUE"
        private const val SAMPLE_VALUE_2 = "SAMPLE_VALUE_2"
    }

    private var cache: InMemoryCache<String>? = null

    @Before
    fun setup() {
        cache = InMemoryCache()
    }

    @After
    fun tearDown() {
        cache = null
    }

    private fun testingCache() = cache!!

    @Test
    fun `empty on start`() {
        Assert.assertTrue(testingCache().isEmpty())
    }

    @Test
    fun `put and not empty`() {
        testingCache().put(SAMPLE_VALUE)

        Assert.assertFalse(testingCache().isEmpty())
    }

    @Test
    fun `put and get is same`() {
        testingCache().put(SAMPLE_VALUE)

        Assert.assertEquals(SAMPLE_VALUE, testingCache().get())
    }

    @Test
    fun `put and put and get`() {
        testingCache().put(SAMPLE_VALUE)
        testingCache().put(SAMPLE_VALUE_2)

        Assert.assertFalse(testingCache().isEmpty())
        Assert.assertEquals(SAMPLE_VALUE_2, testingCache().get())
    }

    @Test
    fun `put and clear`() {
        testingCache().put(SAMPLE_VALUE)
        testingCache().clear()

        Assert.assertTrue(testingCache().isEmpty())
    }
}