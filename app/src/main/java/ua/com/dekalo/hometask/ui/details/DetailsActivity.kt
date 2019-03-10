package ua.com.dekalo.hometask.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import java.io.Serializable

class DetailsActivity : AppCompatActivity() {

    data class State(val title: String) : Serializable

    companion object {

        private const val STATE_KEY = "details_activity_state"

        fun create(context: Context, title: String): Intent {
            return Intent(context, DetailsActivity::class.java).putExtra(STATE_KEY, State(title))
        }
    }

    private lateinit var state: State

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var localState = savedInstanceState?.let { savedInstanceState.getSerializable(STATE_KEY) } as State?
            ?: intent.getSerializableExtra(STATE_KEY) as State?

        if (localState == null) {
            Assertions.fail { IllegalStateException("localState == null") }
            localState = State(getString(R.string.unknown_title))
        }

        state = localState

        initUi()
    }

    private fun initUi() {
        findViewById<TextView>(R.id.details_title_text_view).setText(state.title)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_KEY, state)
    }
}
