package com.stats.mania.di

import com.stats.mania.api.ApiService
import com.stats.mania.api.OpenAiService
import com.stats.mania.viewmodel.AssistantViewModel
import com.stats.mania.viewmodel.CountriesViewModel
import com.stats.mania.viewmodel.CountryIndicatorDetailsViewModel
import com.stats.mania.viewmodel.IndicatorDetailsViewModel
import com.stats.mania.viewmodel.IndicatorViewModel
import com.stats.mania.viewmodel.TopicViewModel
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    val worldBankRetrofit = named("WorldBank")
    val openAiRetrofit = named("OpenAi")

    single(worldBankRetrofit) {
        Retrofit.Builder()
            .baseUrl("https://api.worldbank.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>(worldBankRetrofit).create(ApiService::class.java)
    }

    // OpenAI API Retrofit instance
    single(openAiRetrofit) {
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OpenAiService> {
        get<Retrofit>(openAiRetrofit).create(OpenAiService::class.java)
    }

    // ViewModel instance
    viewModel {
        TopicViewModel(get())
    }

    viewModel {
        IndicatorViewModel(get())
    }

    viewModel {
        IndicatorDetailsViewModel(get())
    }

    viewModel {
        CountriesViewModel(get())
    }

    viewModel {
        CountryIndicatorDetailsViewModel(get())
    }

    viewModel{
        AssistantViewModel(get())
    }

}
