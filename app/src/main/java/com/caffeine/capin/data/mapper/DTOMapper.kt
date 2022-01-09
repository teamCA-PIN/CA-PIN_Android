package com.caffeine.capin.data.mapper

interface DTOMapper<F, T> {
    fun map(from: F): T
}