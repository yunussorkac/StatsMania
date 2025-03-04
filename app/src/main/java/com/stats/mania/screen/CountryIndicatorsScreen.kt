package com.stats.mania.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stats.mania.R
import com.stats.mania.adapter.IndicatorAdapter
import com.stats.mania.adapter.TopicAdapter
import com.stats.mania.viewmodel.IndicatorViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountryIndicatorsScreen(navController: NavHostController, topicId: String, topicValue: String, countryId: String) {
    val context = LocalContext.current
    val indicatorViewModel = koinViewModel<IndicatorViewModel>()

    val indicatorList = indicatorViewModel.indicators.value
    val isLoading = indicatorViewModel.isLoading.value
    val currentPage = indicatorViewModel.currentPage.value
    val listState = rememberLazyListState()
    val totalPages = indicatorViewModel.totalPages.value


    var searchQuery by remember { mutableStateOf("") }
    val filteredIndicators = indicatorList.filter { it.name.contains(searchQuery, ignoreCase = true) }


    // Sayfa verilerini yükle
    LaunchedEffect(currentPage) {
        indicatorViewModel.fetchIndicators(topicId, currentPage)
        listState.scrollToItem(0)
        searchQuery = ""


    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF232526),
                    Color(0xFF414345)
                )
            ))) {
                Text(
                    text = "$topicValue", fontSize = 18.sp, color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                )

                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    },
                    placeholder = { Text(text = "Search indicators", color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(30.dp)) // Oval kenarlar
                        .border(1.dp, Color.White, RoundedCornerShape(30.dp)) // Beyaz çerçeve
                        .background(Color.Transparent), // Şeffaf arka plan
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent, // Alt çizgiyi kaldırır
                        focusedIndicatorColor = Color.Transparent,   // Alt çizgiyi kaldırır
                        disabledIndicatorColor = Color.Transparent,  // Alt çizgiyi kaldırır
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,  // Şeffaf arka plan
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Sol ok (geri)
                    IconButton(onClick = { indicatorViewModel.previousPage(topicId)
                        searchQuery = ""
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, tint = Color.White ,contentDescription = "Previous Page")
                    }

                    Box(modifier = Modifier.weight(1f).padding(top = 10.dp), contentAlignment = Alignment.Center) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = "Page $currentPage of $totalPages",
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    // Sağ ok (ileri)
                    IconButton(onClick = { indicatorViewModel.nextPage(topicId)
                        searchQuery = ""
                    }) {
                        Icon(imageVector = Icons.Default.ArrowForward,tint = Color.White, contentDescription = "Next Page")
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp), state = listState) {
                    items(filteredIndicators) { indicator ->
                        IndicatorAdapter(indicator = indicator, onClick = {
                            navController.navigate("countryIndicatorDetails?indicatorId=${indicator.id}&indicatorName=${indicator.name}" +
                                    "&countryId=${countryId}&sourceNote=${indicator.sourceNote}")
                        })
                    }

                    if (isLoading) {
                        item {
                            Text(text = "Loading...", modifier = Modifier.padding(16.dp), fontSize = 16.sp)
                        }
                    }
                }


            }
        }
    }
}


