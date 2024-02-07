package com.example.sakina.feature_advice.domain.model

data class Advices(
    val data: List<Advice>,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val pageNumber: Int,
    val pageSize: Int,
    val totalCount: Int,
    val totalPages: Int
)