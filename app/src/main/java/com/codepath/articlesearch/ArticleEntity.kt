package com.codepath.articlesearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "foodName") val foodName: String?,
    @ColumnInfo(name = "calories") val calories: String?
)

