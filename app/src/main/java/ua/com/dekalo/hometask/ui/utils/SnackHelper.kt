package ua.com.dekalo.hometask.ui.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackAction(val title: String, val action: () -> Unit)

enum class SnackLength(val length: Int) {
    SHORT(Snackbar.LENGTH_SHORT),
    LONG(Snackbar.LENGTH_LONG),
    INDEFINITE(Snackbar.LENGTH_INDEFINITE)
}

class SnackHelper {

    companion object {
        fun show(
            view: View,
            message: String,
            snackLength: SnackLength = SnackLength.INDEFINITE,
            snackAction: SnackAction? = null
        ) {
            val snack = Snackbar.make(view, message, snackLength.length)

            snackAction?.let { action ->
                snack.setAction(action.title) { action.action() }
            }

            snack.show()
        }
    }
}