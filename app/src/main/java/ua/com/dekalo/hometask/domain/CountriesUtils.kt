package ua.com.dekalo.hometask.domain

class CountriesUtils {
    companion object {

        private const val UNIT = 1000
        private val modifiers = arrayOf("thousands", "millions", "billions")

        fun displayName(name: String, nativeName: String): String {
            return if (name == nativeName) name else "$name ($nativeName)"
        }

        fun humanReadablePopulation(population: Long): String {
            if (population < UNIT) return "$population people"
            val exp = (Math.log(population.toDouble()) / Math.log(UNIT.toDouble())).toInt()
            return String.format("%.1f %s", population / Math.pow(UNIT.toDouble(), exp.toDouble()), modifiers[exp - 1])
        }

    }
}