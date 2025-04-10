package com.stats.mania.screen

import android.graphics.Typeface
import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.stats.mania.R
import com.stats.mania.adapter.IndicatorAdapter
import com.stats.mania.adapter.IndicatorDetailAdapter
import com.stats.mania.model.IndicatorDetail
import com.stats.mania.ui.theme.AppBlue
import com.stats.mania.util.DummyMethods
import com.stats.mania.viewmodel.CountryIndicatorDetailsViewModel
import com.stats.mania.viewmodel.IndicatorDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun CountryIndicatorDetailsScreen(
    navController: NavHostController,
    indicatorId: String,
    indicatorName: String,
    countryId: String,
    sourceNote: String
) {

    val context = LocalContext.current
    val countryIndicatorDetailsViewModel = koinViewModel<CountryIndicatorDetailsViewModel>()
    val indicatorDetailsList = countryIndicatorDetailsViewModel.countryIndicatorDetails.value
    val isLoading = countryIndicatorDetailsViewModel.isLoading.value
    val currentPage = countryIndicatorDetailsViewModel.currentPage.value
    val totalPages = countryIndicatorDetailsViewModel.totalPages.value
    val listState = rememberLazyListState()

    LaunchedEffect(currentPage) {
        countryIndicatorDetailsViewModel.fetchCountryIndicatorDetails(countryId, indicatorId, currentPage)
        listState.scrollToItem(0)
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()
            .background(brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF232526),
                    Color(0xFF414345)
                )
            ))) {

            Text(
                text = "$indicatorName for $countryId",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp, start = 10.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        countryIndicatorDetailsViewModel.previousPage(countryId, indicatorId)
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack,tint = Color.White, contentDescription = "Previous Page")
                }

                Box(modifier = Modifier.weight(1f).padding(top = 10.dp), contentAlignment = Alignment.Center) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.Black
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

                IconButton(
                    onClick = {
                        countryIndicatorDetailsViewModel.nextPage(countryId, indicatorId)
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward,tint = Color.White, contentDescription = "Next Page")
                }

            }



            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.White,
                divider = {

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 }
                ) {
                    Text(
                        text = "Graph",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),

                        )
                }

                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 }
                ) {
                    Text(
                        text = "List",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),

                        )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    Text(
                        text = "$sourceNote",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                    )


                    CustomBarChart(indicatorDetailsList)
                }
                1 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(top = 8.dp),
                        state = listState,
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(indicatorDetailsList) { indicatorDetail ->
                            IndicatorDetailAdapter(indicatorDetail = indicatorDetail, onClick = {})
                        }

                        if (isLoading) {
                            item {
                                Text(
                                    text = "Loading...",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun CustomBarChart(
    indicatorDetailsList: List<IndicatorDetail>,
    modifier: Modifier = Modifier
) {
    val maxValue = indicatorDetailsList.maxOfOrNull { it.value } ?: 1.0
    val barWidth = 50.dp
    val spaceBetweenBars = 10.dp
    val canvasHeight = 400.dp

    val totalBars = indicatorDetailsList.size
    val barWidthPx = with(LocalDensity.current) { barWidth.toPx() }
    val spaceBetweenBarsPx = with(LocalDensity.current) { spaceBetweenBars.toPx() }
    val totalChartWidth = (totalBars * barWidthPx + (totalBars - 1) * spaceBetweenBarsPx).dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(canvasHeight)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(canvasHeight)
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
        ) {
            Canvas(modifier = Modifier.width(totalChartWidth).fillMaxHeight()) {
                val canvasHeightPx = size.height
                val maxBarHeight = canvasHeightPx * 0.8f

                indicatorDetailsList.forEachIndexed { index, detail ->
                    val barHeight = (detail.value / maxValue).toFloat() * maxBarHeight
                    val xOffset = index * (barWidthPx + spaceBetweenBarsPx)
                    val barTop = canvasHeightPx - barHeight

                    val barColor = if (index % 2 == 0) AppBlue else Color(0xFFFFA500)

                    drawRect(
                        color = barColor,
                        topLeft = Offset(xOffset, barTop),
                        size = Size(barWidthPx, barHeight)
                    )

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            DummyMethods.formatNumber(detail.value),
                            xOffset + barWidthPx / 4,
                            barTop - 10f,
                            android.graphics.Paint().apply {
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.LEFT
                                color = android.graphics.Color.WHITE
                            }
                        )
                    }

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            detail.date,
                            xOffset + barWidthPx / 4,
                            canvasHeightPx + 30f,
                            android.graphics.Paint().apply {
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.LEFT
                                color = android.graphics.Color.WHITE
                            }
                        )
                    }
                }
            }
        }

    }
}
