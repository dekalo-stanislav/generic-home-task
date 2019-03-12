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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import ua.com.dekalo.hometask.HomeTaskApplication
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.details.DetailsActivity
import ua.com.dekalo.hometask.ui.utils.AnimatedTransitionUtils
import ua.com.dekalo.hometask.ui.utils.SnackHelper
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var snackBar: Snackbar? = null

    private val adapter = MainActivityAdapter { _, item, view -> onItemClick(item, view) }

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainActivityComponent.builder()
            .appComponent((application as HomeTaskApplication).appComponent)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        AnimatedTransitionUtils.setupForAnimatedTransition(this)

        initUI()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.data.observe(this, Observer { onStateChanged(it) })
        viewModel.loadData()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.main_screen_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData(false) }
    }

    private fun onStateChanged(data: MainActivityData) {

        adapter.updateContent(data.posts)

        if (!data.isLoading) swipeRefreshLayout.isRefreshing = false

        if (data.error != null) {
            snackBar =
                SnackHelper.showNetworkRetrySnackBar(swipeRefreshLayout, data.posts.isEmpty()) {
                    viewModel.loadData(false)
                }
        } else {
            snackBar?.dismiss()
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
                getString(R.string.transition_main_details_post_title_tag)
            )
        )

        startActivity(
            DetailsActivity.create(this, post),
            options.toBundle()
        )
    }
}
