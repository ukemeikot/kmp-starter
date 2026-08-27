package com.ukemeikot.starter.features.home.presentation

import com.ukemeikot.starter.features.auth.domain.model.User

data class HomeState(
    val user: User? = null,
    val isLoading: Boolean = true,
)