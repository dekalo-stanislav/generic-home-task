package ua.com.dekalo.hometask.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.heershingenmosiken.assertions.Assertions
import ua.com.dekalo.hometask.HomeTaskApplication
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Country
import ua.com.dekalo.hometask.ui.ViewModelFactory
import ua.com.dekalo.hometask.ui.utils.AnimatedTransitionUtils
import ua.com.dekalo.hometask.ui.utils.SnackHelper
import java.io.Serializable
import javax.inject.Inject


class DetailsActivity : AppCompatActivity() {

    data class State(val country: Country) : Serializable

    companion object {

        private const val STATE_KEY = "details_activity_state"

        fun create(context: Context, country: Country): Intent {
            return Intent(context, DetailsActivity::class.java).putExtra(STATE_KEY, State(country))
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DetailsActivityViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private var snackBar: Snackbar? = null

    private var state: State? = null
    private val detailsAdapter = DetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerDetailsActivityComponent.builder()
            .appComponent((application as HomeTaskApplication).appComponent)
            .build()
            .inject(this)

        setContentView(R.layout.activity_details)

        state = savedInstanceState?.let { savedInstanceState.getSerializable(STATE_KEY) } as State?
            ?: intent.getSerializableExtra(STATE_KEY) as State?

        state?.let {
            initUi()

            viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsActivityViewModel::class.java)
            viewModel.detailsData.observe(this) { onDataChanged(it) }
            viewModel.init(it.country)
            viewModel.load()

            AnimatedTransitionUtils.setupForAnimatedTransition(this)
            completeAnimatedActivityTransition()

        } ?: Assertions.fail { IllegalStateException("data == null") }
    }

    private fun onDataChanged(data: DetailsActivityData) {

        detailsAdapter.updateItems(data.items)

        if (!data.isLoading) swipeToRefresh.isRefreshing = false

        if (data.error != null) {
            snackBar = SnackHelper.showNetworkRetrySnackBar(swipeToRefresh, data.items.size <= 1) {
                viewModel.load(false)
            }
        } else {
            snackBar?.dismiss()
        }
    }

    private fun completeAnimatedActivityTransition() {
        supportPostponeEnterTransition()
        recyclerView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    if (recyclerView.childCount > 0) {
                        recyclerView.viewTreeObserver.removeOnPreDrawListener(this)
                        supportStartPostponedEnterTransition()
                    }
                    return true
                }
            }
        )
    }

    private fun initUi() {
        recyclerView = findViewById(R.id.details_screen_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = detailsAdapter

        swipeToRefresh = findViewById(R.id.swipe_to_refresh)
        swipeToRefresh.setOnRefreshListener { viewModel.load(false) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(STATE_KEY, state)
    }
}
