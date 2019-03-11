package ua.com.dekalo.hometask.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post
import java.io.Serializable

class DetailsActivity : AppCompatActivity() {

    data class State(val post: Post) : Serializable

    companion object {

        private const val STATE_KEY = "details_activity_state"

        fun create(context: Context, post: Post): Intent {
            return Intent(context, DetailsActivity::class.java).putExtra(STATE_KEY, State(post))
        }
    }

    private lateinit var viewModel: DetailsActivityViewModel

    private var state: State? = null
    private val detailsAdapter = DetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        state = savedInstanceState?.let { savedInstanceState.getSerializable(STATE_KEY) } as State?
            ?: intent.getSerializableExtra(STATE_KEY) as State?

        state?.let {
            initUi()

            viewModel = ViewModelProviders.of(this).get(DetailsActivityViewModel::class.java)
            viewModel.detailsContent.observe(this, Observer { detailsAdapter.updateItems(it) })
            viewModel.init(it.post)
        } ?: Assertions.fail { IllegalStateException("state == null") }
    }

    private fun initUi() {
        val recyclerView = findViewById<RecyclerView>(R.id.details_screen_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = detailsAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_KEY, state)
    }
}
