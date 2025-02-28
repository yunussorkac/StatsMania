package com.stats.mania.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.stats.mania.api.ApiService
import com.stats.mania.model.Meta
import com.stats.mania.model.Topic
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TopicViewModel(private val apiService: ApiService) : ViewModel() {

    val topics = mutableStateOf<List<Topic>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)

    fun fetchTopics() {
        viewModelScope.launch {
            try {
                val response = apiService.getTopics()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.size == 2) {
                        val meta = Gson().fromJson(Gson().toJson(body[0]), Meta::class.java)
                        val dataList = Gson().fromJson(Gson().toJson(body[1]), Array<Topic>::class.java).toList()

                        topics.value = dataList
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
            }
        }
    }

}

