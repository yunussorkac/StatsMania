package com.stats.mania.model

data class Country(
    val id: String,
    val iso2Code: String,
    val name: String,
    val region: Region,
    val adminregion: Adminregion,
    val incomeLevel: IncomeLevel,
    val lendingType: LendingType,
    val capitalCity: String,
    val longitude: String,
    val latitude: String,
)

data class Region(
    val id: String,
    val iso2code: String,
    val value: String,
)

data class Adminregion(
    val id: String,
    val iso2code: String,
    val value: String,
)

data class IncomeLevel(
    val id: String,
    val iso2code: String,
    val value: String,
)

data class LendingType(
    val id: String,
    val iso2code: String,
    val value: String,
)
