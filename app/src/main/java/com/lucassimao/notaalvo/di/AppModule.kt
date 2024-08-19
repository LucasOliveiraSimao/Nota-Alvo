package com.lucassimao.notaalvo.di

import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import com.lucassimao.notaalvo.domain.usecase.CalculateScoreUseCase
import com.lucassimao.notaalvo.domain.usecase.EvaluateScoreUseCase
import com.lucassimao.notaalvo.preferences.AppPreferences
import com.lucassimao.notaalvo.presentation.ads.AdManager
import com.lucassimao.notaalvo.presentation.calculator.CalculatorViewModel
import com.lucassimao.notaalvo.presentation.review.FeedbackManager
import com.lucassimao.notaalvo.presentation.review.ReviewFlowManager
import com.lucassimao.notaalvo.util.Constants.KEY_APP_USE_COUNT
import com.lucassimao.notaalvo.util.Constants.PREFS_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        val context: Context = get()
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    single<Int> {
        val prefs: SharedPreferences = get()
        prefs.getInt(KEY_APP_USE_COUNT, 0)
    }

    single { AppPreferences(get()) }
}

val adModule = module {
    single { (context: Context, adContainer: ViewGroup) ->
        AdManager(context, adContainer)
    }
}

val reviewModule = module {
    single { (context: Context) -> ReviewFlowManager(context) }
}

val feedbackModule = module {
    single { AppPreferences(get()) }
    single { ReviewFlowManager(get()) }

    single { FeedbackManager(get(), get()) }
}

val viewModelModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
}

val useCaseModule = module {
    single { CalculateScoreUseCase() }
    single { EvaluateScoreUseCase() }
}