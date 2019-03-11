package ua.com.dekalo.hometask.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ua.com.dekalo.hometask.HomeTaskApplication
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.DataModel
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.details.DetailsActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val adapter = MainActivityAdapter { _, item -> onItemClick(item) }

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMainActivityComponent.builder()
            .appComponent((application as HomeTaskApplication).appComponent)
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel.data.observe(this, Observer { onDataUpdated(it) })
        viewModel.isLoading.observe(this, Observer { if (!it) swipeRefreshLayout.isRefreshing = false })
        viewModel.loadData()
    }

    private fun initUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.main_screen_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh)
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData(false) }
    }

    private fun onDataUpdated(dataModel: DataModel) {
        adapter.updateContent(dataModel.posts)
    }

    private fun onItemClick(post: Post) {
        startActivity(DetailsActivity.create(this, post))
    }
}
