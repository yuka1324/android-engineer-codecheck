package jp.co.yumemi.android.code_check.data

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class SearchResultContents(
    val full_name: String?,
    val owner: SearchResultOwnerContents?,
    val language: String?,
    val stargazers_count: Long?,
    val watchers_count: Long?,
    val forks_count: Long?,
    val open_issues_count: Long?,
) : Serializable

@JsonClass(generateAdapter = true)
data class SearchResultOwnerContents(
    val avatar_url: String?,
    val html_url: String?
)