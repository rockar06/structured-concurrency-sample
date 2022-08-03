package com.example.structuredconcurrencysample.data

import kotlinx.coroutines.delay

class SectionRepositoryImpl : SectionRepository {

    private var errorCounter = 0

    override suspend fun getTopPost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded()
        return Section(
            type = SectionType.TOP,
            isLoading = false,
            title = "Top post!"
        )
    }

    override suspend fun getMiddlePost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded()
        return Section(
            type = SectionType.MIDDLE,
            isLoading = false,
            title = "Middle post!"
        )
    }

    override suspend fun getBottomPost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded()
        return Section(
            type = SectionType.BOTTOM,
            isLoading = false,
            title = "Bottom post!"
        )
    }

    private fun throwExceptionIfNeeded() {
        errorCounter++
        if (errorCounter % 5 == 0) {
            errorCounter = 0
            throw RuntimeException("Ups! I had to do it, believe me.")
        }
    }
}