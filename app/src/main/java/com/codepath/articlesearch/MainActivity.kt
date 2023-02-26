package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private  lateinit var entryButton: Button
    private lateinit var binding: ActivityMainBinding
    lateinit var swipeContainer: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setContentView(R.layout.activity_main)

        // Lookup the swipe container view

        swipeContainer = findViewById(R.id.swiperefresh)

        /*swipeContainer.setOnRefreshListener {
            articlesRecyclerView = findViewById(R.id.articles)

            val articleAdapter = ArticleAdapter(this, articles)
            articlesRecyclerView.adapter = articleAdapter


            lifecycleScope.launch {
                (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                    databaseList.map { entity ->
                        DisplayArticle(
                            entity.foodName,
                            entity.calories
                        )
                    }.also { mappedList ->
                        articles.clear()
                        articles.addAll(mappedList)
                        articleAdapter.notifyDataSetChanged()
                    }
                }
            }
            swipeContainer.isRefreshing=false
        }*/




        articlesRecyclerView = findViewById(R.id.articles)
        entryButton = findViewById(R.id.newEntryButton)

        val articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter


        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayArticle(
                        entity.foodName,
                        entity.calories
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }
        entryButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            this.startActivity(intent)
        }



        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

    }


}