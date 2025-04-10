package com.stats.mania.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stats.mania.api.ApiService
import com.stats.mania.api.OpenAiService
import com.stats.mania.model.ChatRequest
import com.stats.mania.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class AssistantViewModel(private val aiService: OpenAiService) : ViewModel() {


    fun sendMessageToOpenAi(input : String, onResponse : (String) -> Unit) {
        viewModelScope.launch {
            val request = ChatRequest(
                model = "gpt-4o-mini",
                messages = listOf(Message(role = "user", content = input))
            )

            try {
                val response = withContext(Dispatchers.IO) {
                    aiService.createChatCompletion(request).awaitResponse()
                }

                if (response.isSuccessful) {
                    val answer = response.body()?.choices?.firstOrNull()?.message?.content
                    onResponse(answer.orEmpty())
                    Log.d("OpenAiResponse", "Yanıt: $answer")
                } else {
                    Log.e("OpenAiResponse", "Hata: ${response.errorBody()?.string()}")
                }

            } catch (e: Exception) {
                Log.e("OpenAiException", "İstisna: ${e.message}")
            }
        }
    }



}


