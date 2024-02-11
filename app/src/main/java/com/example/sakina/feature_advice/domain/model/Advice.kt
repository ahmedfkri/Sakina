package com.example.sakina.feature_advice.domain.model

import java.io.Serializable

data class Advice(
    val author: Author,
    val content: String,
    val id: Int,
    val imageUrl: String,
    val publishedOn: String,
    val title: String
) : Serializable