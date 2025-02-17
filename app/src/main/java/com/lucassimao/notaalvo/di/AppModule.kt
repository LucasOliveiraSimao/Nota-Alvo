package com.lucassimao.notaalvo.di

import android.content.Context
import android.content.SharedPreferences
import android.view.ViewGroup
import com.lucassimao.notaalvo.domain.usecase.CalculateScoreUseCase
import com.lucassimao.notaalvo.domain.usecase.EvaluateScoreUseCase
import com.lucassimao.notaalvo.preferences.AppPreferences
import com.lucassimao.notaalvo.presentation.ads.AdManager
import com.lucassimao.notaalvo.presentation.calculator.CalculatorViewModel
import com.lucassimao.notaalvo.presentation.onboarding.OnboardingManager
import com.lucassimao.notaalvo.presentation.review.FeedbackManager
import com.lucassimao.notaalvo.presentation.review.ReviewManager
import com.lucassimao.notaalvo.util.Constants.PREFS_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        val context: Context = get()
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    single { AppPreferences(get()) }
}

val adModule = module {
    single { (context: Context, adContainer: ViewGroup) ->
        AdManager(context, adContainer)
    }
}

val reviewModule = module {
    single { (context: Context) -> ReviewManager(context) }
}

val feedbackModule = module {
    single { AppPreferences(get()) }
    single { ReviewManager(get()) }

    single { FeedbackManager(get(), get()) }
}

val viewModelModule = module {
    viewModel { CalculatorViewModel(get(), get()) }
}

val useCaseModule = module {
    single { CalculateScoreUseCase() }
    single { EvaluateScoreUseCase() }
}

val onboardingModule = module {
    single { OnboardingManager(get()) }
}