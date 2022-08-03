package com.example.structuredconcurrencysample.data

data class Section(
    var type: SectionType = SectionType.TOP,
    var isLoading: Boolean = true,
    var title: String? = null,
    var errorMessage: String? = null,
)


enum class SectionType {
    TOP,
    MIDDLE,
    BOTTOM
}
