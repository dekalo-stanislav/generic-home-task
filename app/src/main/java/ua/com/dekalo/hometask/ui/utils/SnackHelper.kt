package ua.com.dekalo.hometask.ui.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ua.com.dekalo.hometask.R

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
        ): Snackbar {
            val snack = Snackbar.make(view, message, snackLength.length)

            snackAction?.let { action ->
                snack.setAction(action.title) { action.action() }
            }

            snack.show()

            return snack
        }

        fun showNetworkRetrySnackBar(view: View, blocking: Boolean = false, retryAction: () -> Unit): Snackbar {
            return if (blocking) {
                SnackHelper.show(
                    view,
                    view.context.getString(R.string.failed_to_load_content),
                    snackAction = SnackAction(view.context.getString(R.string.retry_snackbar_action)) { retryAction() }
                )
            } else {
                SnackHelper.show(
                    view,
                    view.context.getString(R.string.failed_to_refresh_content),
                    snackLength = SnackLength.SHORT,
                    snackAction = SnackAction(view.context.getString(R.string.retry_snackbar_action)) { retryAction() }
                )
            }
        }
    }
}