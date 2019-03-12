package ua.com.dekalo.hometask

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ua.com.dekalo.hometask.cache.InMemoryKeyBasedCache

class InMemoryKeyBasedCacheTest {

    companion object {
        private const val SAMPLE_KEY = "SAMPLE_KEY"
        private const val SAMPLE_VALUE = "SAMPLE_VALUE"
        private const val SAMPLE_KEY_2 = "SAMPLE_KEY_2"
        private const val SAMPLE_VALUE_2 = "SAMPLE_VALUE_2"
        private const val SAMPLE_KEY_3 = "SAMPLE_KEY_3"
    }

    private var cache: InMemoryKeyBasedCache<String, String>? = null

    @Before
    fun setup() {
        cache = InMemoryKeyBasedCache()
    }

    @After
    fun tearDown() {
        cache = null
    }

    private fun testingCache() = cache!!

    @Test
    fun `empty on start`() {
        Assert.assertTrue(testingCache().isEmpty())
        Assert.assertTrue(testingCache().isEmpty("test_key"))
    }

    @Test
    fun `put and check`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)

        Assert.assertFalse(testingCache().isEmpty())
        Assert.assertFalse(testingCache().isEmpty(SAMPLE_KEY))
        Assert.assertTrue(testingCache().isEmpty(SAMPLE_KEY_2))
    }

    @Test
    fun `put and get`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)

        Assert.assertEquals(SAMPLE_VALUE, testingCache().get(SAMPLE_KEY))
    }

    @Test
    fun `put and put and get`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE_2)

        Assert.assertEquals(SAMPLE_VALUE_2, testingCache().get(SAMPLE_KEY))
    }

    @Test
    fun `put and clear item`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)
        testingCache().clear(SAMPLE_KEY)

        Assert.assertTrue(testingCache().isEmpty())
        Assert.assertTrue(testingCache().isEmpty(SAMPLE_KEY))
    }

    @Test
    fun `put and clear all`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)
        testingCache().clear()

        Assert.assertTrue(testingCache().isEmpty())
        Assert.assertTrue(testingCache().isEmpty(SAMPLE_KEY))
    }

    @Test
    fun `put multiple items`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)
        testingCache().put(SAMPLE_KEY_2, SAMPLE_VALUE_2)

        Assert.assertEquals(SAMPLE_VALUE, testingCache().get(SAMPLE_KEY))
        Assert.assertEquals(SAMPLE_VALUE_2, testingCache().get(SAMPLE_KEY_2))
    }

    @Test
    fun `put multiple and clear single item`() {
        testingCache().put(SAMPLE_KEY, SAMPLE_VALUE)
        testingCache().put(SAMPLE_KEY_2, SAMPLE_VALUE_2)
        testingCache().clear(SAMPLE_KEY)

        Assert.assertNull(testingCache().get(SAMPLE_KEY))
        Assert.assertEquals(SAMPLE_VALUE_2, testingCache().get(SAMPLE_KEY_2))
        Assert.assertTrue(testingCache().isEmpty(SAMPLE_KEY))
        Assert.assertFalse(testingCache().isEmpty())
        Assert.assertFalse(testingCache().isEmpty(SAMPLE_KEY_2))
    }
}