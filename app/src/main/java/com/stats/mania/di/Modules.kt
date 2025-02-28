package com.stats.mania.di

import com.stats.mania.api.ApiService
import com.stats.mania.viewmodel.CountriesViewModel
import com.stats.mania.viewmodel.CountryIndicatorDetailsViewModel
import com.stats.mania.viewmodel.IndicatorDetailsViewModel
import com.stats.mania.viewmodel.IndicatorViewModel
import com.stats.mania.viewmodel.TopicViewModel
import okhttp3.OkHttpClient
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl("https://api.worldbank.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //ApiService instance
    single {
        get<Retrofit>().create(ApiService::class.java)
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

}
