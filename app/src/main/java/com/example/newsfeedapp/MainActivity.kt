package com.example.newsfeedapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeedapp.adapter.NewsAdapter
import com.example.newsfeedapp.utils.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        progressBar = findViewById(R.id.progressBar)

        newsRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchNews()
    }

    private fun fetchNews() {
        progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getTopHeadlines()
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.GONE
                newsRecyclerView.adapter = NewsAdapter(response.articles)
            }
        }
    }
}
