package com.ukemeikot.starter.core.di

import com.ukemeikot.starter.core.storage.createPreferenceStore
import com.ukemeikot.starter.features.auth.data.local.AuthPreferences
import com.ukemeikot.starter.features.onboarding.data.OnboardingPreferences
import org.koin.core.module.Module
import org.koin.dsl.module

fun preferencesModule(context: Any?): Module = module {
    single { createPreferenceStore(context) }
    single { OnboardingPreferences(get()) }
    single { AuthPreferences(get()) }
}
