package com.example.structuredconcurrencysample.data

interface SectionRepository {

    suspend fun getTopPost(delayTime: Long = 3000L): Section

    suspend fun getMiddlePost(delayTime: Long = 3000L): Section

    suspend fun getBottomPost(delayTime: Long = 3000L): Section
}