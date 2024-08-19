package com.lucassimao.notaalvo

import android.app.Application
import com.lucassimao.notaalvo.di.adModule
import com.lucassimao.notaalvo.di.appModule
import com.lucassimao.notaalvo.di.feedbackModule
import com.lucassimao.notaalvo.di.reviewModule
import com.lucassimao.notaalvo.di.useCaseModule
import com.lucassimao.notaalvo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                adModule,
                reviewModule,
                feedbackModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}