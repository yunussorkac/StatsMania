package com.stats.mania.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.stats.mania.api.ApiService
import com.stats.mania.model.Indicator
import com.stats.mania.model.Meta
import com.stats.mania.model.Topic
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class IndicatorViewModel(private val apiService: ApiService) : ViewModel() {

    val indicators = mutableStateOf<List<Indicator>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    val currentPage = mutableStateOf(1)
    val totalPages = mutableStateOf(1)
    val isLoading = mutableStateOf(false)

    // Sayfa verilerini çekme fonksiyonu
    fun fetchIndicators(id: String, page: Int) {
        if (isLoading.value || page > totalPages.value) return

        isLoading.value = true
        Log.d("IndicatorViewModel", "Fetching indicators for page $page with topicId: $id")

        viewModelScope.launch {
            try {
                val response = apiService.getIndicators(id, page)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.size == 2) {
                        val dataList = Gson().fromJson(Gson().toJson(body[1]), Array<Indicator>::class.java).toList()
                        Log.d("IndicatorViewModel", "Received ${dataList.size} indicators for page $page")

                        indicators.value = if (page == 1) dataList else dataList

                        // Toplam sayfa sayısını belirle
                        val metaData = Gson().fromJson(Gson().toJson(body[0]), Meta::class.java)
                        totalPages.value = metaData.pages
                        Log.d("IndicatorViewModel", "Total Pages: ${totalPages.value}")
                    }
                } else {
                    errorMessage.value = "Error: ${response.code()}"
                    Log.e("API Error", "Response Code: ${response.code()}")
                }
            } catch (e: HttpException) {
                errorMessage.value = "HTTP Error: ${e.message()}"
                Log.e("API Error", e.message.orEmpty())
            } catch (e: IOException) {
                errorMessage.value = "Network Error: ${e.message}"
                Log.e("API Error", e.message.orEmpty())
            } finally {
                isLoading.value = false
            }
        }
    }

    // Sayfayı ileri al
    fun nextPage(id: String) {
        if (currentPage.value < totalPages.value) {
            currentPage.value++
            fetchIndicators(id, currentPage.value)
        }
    }

    // Sayfayı geri al
    fun previousPage(id: String) {
        if (currentPage.value > 1) {
            currentPage.value--
            fetchIndicators(id, currentPage.value)
        }
    }
}





