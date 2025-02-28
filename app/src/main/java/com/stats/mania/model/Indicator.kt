package com.stats.mania.model


data class Indicator(
    val id: String,
    val name: String,
    val unit: String,
    val source: Source,
    val sourceNote: String,
    val sourceOrganization: String,
    val topics: List<Topic>
)

