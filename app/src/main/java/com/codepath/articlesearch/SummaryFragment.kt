package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


class SummaryFragment : Fragment() {

    private val articles = mutableListOf<DisplayArticle>()
    private  lateinit var entryButton: Button
    private lateinit var calCount: TextView
    private lateinit var mealCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        fetchArticles()
        calCount = view.findViewById(R.id.calorieCount)
        mealCount = view.findViewById(R.id.mealCount)
        var mealC = 0
        var calC = 0
        for (article in articles){
            mealC += 1
            val x = article.calories?.toIntOrNull()
            if(x!=null){
                calC += x
                calC /= mealC
            }
        }
        mealCount.text = mealC.toString()
        calCount.text = calC.toString()+" per Meal"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        entryButton = view.findViewById(R.id.newEntryButton)

        // Add these configurations for the recyclerView and to configure the adapter
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
                }
            }
        }
    }

    companion object {
        fun newInstance(): SummaryFragment{
            return SummaryFragment()
        }
    }
}