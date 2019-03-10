package ua.com.dekalo.hometask.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post
import java.io.Serializable
import java.lang.IllegalStateException

class DetailsActivity : AppCompatActivity() {

    data class State(val post: Post) : Serializable

    companion object {

        private const val STATE_KEY = "details_activity_state"

        fun create(context: Context, post: Post): Intent {
            return Intent(context, DetailsActivity::class.java).putExtra(STATE_KEY, State(post))
        }
    }

    private var state: State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        state = savedInstanceState?.let { savedInstanceState.getSerializable(STATE_KEY) } as State?
            ?: intent.getSerializableExtra(STATE_KEY) as State?

        state?.let { initUi(it) }

        if (state == null) {
            Assertions.fail { IllegalStateException("state == null") }
        }
    }

    private fun initUi(state: State) {
        findViewById<TextView>(R.id.details_title_text_view).text = state.post.title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_KEY, state)
    }
}
