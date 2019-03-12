package ua.com.dekalo.hometask.ui.details

data class DetailsActivityData(
    val items: List<DetailsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null
)