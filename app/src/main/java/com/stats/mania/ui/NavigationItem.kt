package com.stats.mania.ui

import com.stats.mania.R

sealed class NavigationItem(val route: String, val title: String, val icon: Int) {
    data object Topics : NavigationItem("topics", "Topics", R.drawable.topic)
    data object Countries : NavigationItem("countries", "Countries", R.drawable.flag)

}