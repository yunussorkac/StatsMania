package com.stats.mania.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stats.mania.adapter.CountryAdapter
import com.stats.mania.adapter.IndicatorAdapter
import com.stats.mania.viewmodel.CountriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountriesScreen(navController : NavHostController) {

    val context = LocalContext.current
    val countriesViewModel = koinViewModel<CountriesViewModel>()

    val countryList = countriesViewModel.countries.value
    val isLoading = countriesViewModel.isLoading.value
    val currentPage = countriesViewModel.currentPage.value
    val listState = rememberLazyListState()
    val totalPages = countriesViewModel.totalPages.value

    LaunchedEffect(currentPage) {
        countriesViewModel.fetchCountries(currentPage)
        listState.scrollToItem(0)
    }



    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Sol ok (geri)
                    IconButton(onClick = { countriesViewModel.previousPage() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Page")
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
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    // Sağ ok (ileri)
                    IconButton(onClick = { countriesViewModel.nextPage() }) {
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Page")
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp), state = listState) {
                    items(countryList) { country ->
                        CountryAdapter(country = country, onClick = {
                            navController.navigate("countryTopics?countryId=${country.id}&countryName=${country.name}")

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