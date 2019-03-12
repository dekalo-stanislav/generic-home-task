package ua.com.dekalo.hometask.ui.main

import android.app.ActivityOptions
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.com.dekalo.hometask.HomeTaskApplication
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.details.DetailsActivity
import ua.com.dekalo.hometask.ui.utils.SnackAction
import ua.com.dekalo.hometask.ui.utils.SnackHelper
import ua.com.dekalo.hometask.ui.utils.SnackLength
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val adapter = MainActivityAdapter { _, item, view -> onItemClick(item, view) }

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainActivityComponent.builder()
            .appComponent((application as HomeTaskApplication).appComponent)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initUI()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.state.observe(this, Observer { onStateChanged(it) })
        viewModel.loadData()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.main_screen_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData(false) }
    }

    private fun onStateChanged(state: MainActivityState) {

        adapter.updateContent(state.posts)

        if (!state.isLoading) swipeRefreshLayout.isRefreshing = false

        if (state.error != null) {
            if (state.posts.isEmpty()) {
                SnackHelper.show(
                    swipeRefreshLayout,
                    getString(R.string.failed_to_load_content),
                    snackAction = SnackAction(getString(R.string.retry_snackbar_action)) { viewModel.loadData() }
                )
            } else {
                SnackHelper.show(
                    swipeRefreshLayout,
                    getString(R.string.failed_to_refresh_content),
                    snackLength = SnackLength.SHORT,
                    snackAction = SnackAction(getString(R.string.retry_snackbar_action)) { viewModel.loadData() }
                )
            }
        }
    }

    private fun onItemClick(post: Post, view: View) {

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair.create(
                view.findViewById(R.id.post_author_text_view),
                getString(R.string.transition_main_details_author_name_tag)
            ),
            Pair.create(
                view.findViewById(R.id.post_title_text_view),
                getString(R.string.transtion_main_details_post_title_tag)
            )
        )

        startActivity(
            DetailsActivity.create(this, post),
            options.toBundle()
        )
    }
}
