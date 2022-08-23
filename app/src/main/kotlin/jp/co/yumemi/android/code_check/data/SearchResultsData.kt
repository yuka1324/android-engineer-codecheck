package jp.co.yumemi.android.code_check.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResultsData(
    val items: List<SearchResultContents>
)
