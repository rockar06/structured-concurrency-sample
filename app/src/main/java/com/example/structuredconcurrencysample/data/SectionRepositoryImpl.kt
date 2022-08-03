package com.example.structuredconcurrencysample.data

import kotlinx.coroutines.delay

class SectionRepositoryImpl : SectionRepository {

    private var errorCounter = 0

    override suspend fun getTopPost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded(TopSectionException)
        return Section(
            type = SectionType.TOP,
            isLoading = false,
            title = "Top post!"
        )
    }

    override suspend fun getMiddlePost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded(MiddleSectionException)
        return Section(
            type = SectionType.MIDDLE,
            isLoading = false,
            title = "Middle post!"
        )
    }

    override suspend fun getBottomPost(delayTime: Long): Section {
        delay(delayTime)
        throwExceptionIfNeeded(BottomSectionException)
        return Section(
            type = SectionType.BOTTOM,
            isLoading = false,
            title = "Bottom post!"
        )
    }

    private fun throwExceptionIfNeeded(exceptionToThrow: RuntimeException) {
        errorCounter++
        if (errorCounter % 5 == 0) {
            errorCounter = 0
            throw exceptionToThrow
        }
    }
}

object TopSectionException : RuntimeException("Exception from the top section")
object MiddleSectionException : RuntimeException("Exception from the middle section")
object BottomSectionException : RuntimeException("Exception from the bottom section")