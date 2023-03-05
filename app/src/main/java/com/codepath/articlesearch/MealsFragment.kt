package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [MealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealsFragment : Fragment() {

    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private  lateinit var entryButton: Button
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchArticles()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_meals, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        articlesRecyclerView = view.findViewById(R.id.articles)
        articlesRecyclerView.layoutManager = layoutManager
        articlesRecyclerView.setHasFixedSize(true)
        articleAdapter = ArticleAdapter(view.context, articles)
        articlesRecyclerView.adapter = articleAdapter
        entryButton = view.findViewById(R.id.newEntryButton)
        entryButton.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            this.startActivity(intent)
        }
        // Update the return statement to return the inflated view from above
        return view
    }
    private fun fetchArticles() {
        lifecycleScope.launch {
            (activity?.application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
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
    }

    companion object {
        fun newInstance(): MealsFragment{
            return MealsFragment()
        }
    }
}