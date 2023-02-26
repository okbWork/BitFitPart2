package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.IO

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var calorieTextView: EditText
    private lateinit var foodNameTextView: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_ui)

        // TODO: Find the views for the screen
        calorieTextView = findViewById(R.id.calInput)
        foodNameTextView = findViewById(R.id.nameInput)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val cals = calorieTextView.text.toString()
            val name = foodNameTextView.text.toString()
            if(name.isNotEmpty()){
                val entryItem = Article(name, cals)
                lifecycleScope.launch(IO) {
                    (application as ArticleApplication).db.articleDao().insertAll(
                        listOf<ArticleEntity>(
                            ArticleEntity(
                                foodName = name,
                                calories = cals
                            )
                        )
                    )
                }
                finish()
            }
        }
    }
}