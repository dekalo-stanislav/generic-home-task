package ua.com.dekalo.hometask.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ua.com.dekalo.hometask.R
import ua.com.dekalo.hometask.models.DataModel
import ua.com.dekalo.hometask.models.Post
import ua.com.dekalo.hometask.ui.details.DetailsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private val adapter = MainActivityAdapter { _, item -> onItemClick(item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()

        // TODO different model injection
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.data.observe(this, Observer { onDataUpdated(it) })
        viewModel.loadData()
    }

    private fun initRecycler() {
        val recyclerView = findViewById<RecyclerView>(R.id.main_screen_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun onDataUpdated(dataModel: DataModel) {
        adapter.updateContent(dataModel.posts)
    }

    private fun onItemClick(post: Post) {
        startActivity(DetailsActivity.create(this, post))
    }
}
