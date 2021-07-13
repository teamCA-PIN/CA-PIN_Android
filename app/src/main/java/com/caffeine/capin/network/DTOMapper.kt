package com.caffeine.capin.network

interface DTOMapper<F, T> {
    fun map(from: F): T
}