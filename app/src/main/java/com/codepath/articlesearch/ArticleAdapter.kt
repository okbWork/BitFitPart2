package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

const val ARTICLE_EXTRA = "ARTICLE_EXTRA"
private const val TAG = "ArticleAdapter"

class ArticleAdapter(private val context: Context, private val articles: List<DisplayArticle>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.bitfit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }



    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val calorieTextView = itemView.findViewById<TextView>(R.id.calsTv)
        private val foodNameTextView = itemView.findViewById<TextView>(R.id.nameTv)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(article: DisplayArticle) {
            calorieTextView.text = article.calories
            foodNameTextView.text = article.foodName
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }


        /*override fun onClick(v: View?) {
            // Get selected article
            val article = articles[absoluteAdapterPosition]

            //  Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
        }*/


    }
}