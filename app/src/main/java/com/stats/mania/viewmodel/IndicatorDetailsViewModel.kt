package com.stats.mania.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stats.mania.api.ApiService
import com.stats.mania.model.Indicator
import com.stats.mania.model.IndicatorDetail
import com.stats.mania.model.Meta
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class IndicatorDetailsViewModel(private val apiService: ApiService) : ViewModel() {

    val indicatorDetails = mutableStateOf<List<IndicatorDetail>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    val currentPage = mutableIntStateOf(1)
    val totalPages = mutableIntStateOf(1) // Yeni değişken eklendi
    val isLoading = mutableStateOf(false)

    fun fetchIndicatorDetails(id: String, page: Int) {
        if (isLoading.value || page > totalPages.intValue) return // Son sayfadaysak yeni istek yapma

        isLoading.value = true
        Log.d("IndicatorViewModel", "Fetching indicators for page $page with topicId: $id")

        viewModelScope.launch {
            try {
                val response = apiService.getIndicatorDetails(id, page)
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body.isNullOrEmpty() || body.size < 2) {
                        Log.e("API Error", "Geçersiz JSON formatı: body boş veya eksik!")
                        return@launch
                    }

                    val jsonElement = body[1]
                    val jsonString = Gson().toJson(jsonElement)

                    // JSON verisini güvenli bir şekilde listeye dönüştür
                    val type = object : TypeToken<List<IndicatorDetail>>() {}.type
                    val dataList: List<IndicatorDetail>? = try {
                        Gson().fromJson(jsonString, type)
                    } catch (e: Exception) {
                        Log.e("JSON_ERROR", "JSON dönüşüm hatası: ${e.message}")
                        null
                    }

                    if (dataList.isNullOrEmpty()) {
                        Log.e("API Error", "Dönüşen veri listesi boş!")
                        return@launch
                    }

                    Log.d("IndicatorViewModel", "Received ${dataList.size} indicators for page $page")

                    // İlk sayfada önceki verileri sıfırla, yoksa ekle
                    indicatorDetails.value = if (page == 1) dataList else dataList

                    // Meta verisini güvenli bir şekilde al
                    val metaDataJson = Gson().toJson(body[0])
                    val metaData: Meta? = try {
                        Gson().fromJson(metaDataJson, Meta::class.java)
                    } catch (e: Exception) {
                        Log.e("JSON_ERROR", "Meta verisi dönüşüm hatası: ${e.message}")
                        null
                    }

                    if (metaData != null) {
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


    fun nextPage(id: String) {
        if (currentPage.intValue < totalPages.intValue) {
            currentPage.intValue++
            fetchIndicatorDetails(id, currentPage.intValue)
        }
    }

    // Sayfayı geri al
    fun previousPage(id: String) {
        if (currentPage.intValue > 1) {
            currentPage.intValue--
            fetchIndicatorDetails(id, currentPage.intValue)
        }
    }

}

