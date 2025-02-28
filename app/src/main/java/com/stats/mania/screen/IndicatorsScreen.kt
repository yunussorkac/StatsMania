package com.stats.mania.screen

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun IndicatorsScreen(navController: NavHostController, topicId: String, topicValue: String) {
    val context = LocalContext.current
    val indicatorViewModel = koinViewModel<IndicatorViewModel>()

    val indicatorList = indicatorViewModel.indicators.value
    val isLoading = indicatorViewModel.isLoading.value
    val currentPage = indicatorViewModel.currentPage.value
    val listState = rememberLazyListState()
    val totalPages = indicatorViewModel.totalPages.value

    // Sayfa verilerini yükle
    LaunchedEffect(currentPage) {
        indicatorViewModel.fetchIndicators(topicId, currentPage)
        listState.scrollToItem(0)

    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "$topicValue", fontSize = 18.sp, color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Sol ok (geri)
                    IconButton(onClick = { indicatorViewModel.previousPage(topicId) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Page")
                    }

                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = "Page $currentPage of $totalPages",
                                fontSize = 16.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    // Sağ ok (ileri)
                    IconButton(onClick = { indicatorViewModel.nextPage(topicId) }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Page")
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp), state = listState) {
                    items(indicatorList) { indicator ->
                        IndicatorAdapter(indicator = indicator, onClick = {
                            navController.navigate("indicatorDetails?indicatorId=${indicator.id}&indicatorName=${indicator.name}" +
                                    "&sourceNote=${indicator.sourceNote}")
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


