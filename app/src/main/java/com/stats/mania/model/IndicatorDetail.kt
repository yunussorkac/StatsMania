package com.stats.mania.model

data class IndicatorDetail(
    val indicator: IndicatorDetails,
    val country: CountryDetails,
    val countryIso3Code: String,
    val date: String,
    val value: Double,
    val unit: String,
    val obsStatus: String,
    val decimal: Int
)

data class IndicatorDetails(
    val id: String,
    val value: String
)

data class CountryDetails(
    val id: String,
    val value: String
)
