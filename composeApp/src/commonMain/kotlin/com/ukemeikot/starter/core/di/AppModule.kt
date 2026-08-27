package com.ukemeikot.starter.core.di

import com.ukemeikot.starter.features.auth.di.authModule
import com.ukemeikot.starter.features.home.di.homeModule
import com.ukemeikot.starter.features.onboarding.di.onboardingModule
import org.koin.dsl.KoinAppDeclaration

fun appDeclaration(context: Any? = null): KoinAppDeclaration = {
    modules(
        coreModule(context = context),
        preferencesModule(context = context),
        onboardingModule(),
        authModule(),
        homeModule(),
    )
}
