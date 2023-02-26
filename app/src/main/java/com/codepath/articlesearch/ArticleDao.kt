package com.codepath.articlesearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM entry_table")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert
    fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM entry_table")
    fun deleteAll()
}

