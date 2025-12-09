package com.cybershield.data.model

import java.util.Date

data class NewsItem(
    val id: String,
    val title: String,
    val source: String,
    val description: String,
    val date: Date,
    val category: NewsCategory
)

enum class NewsCategory {
    INTERNATIONAL, NATIONAL, LOCAL
}

