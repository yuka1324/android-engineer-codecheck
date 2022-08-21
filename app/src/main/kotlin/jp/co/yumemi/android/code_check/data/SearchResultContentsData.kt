package jp.co.yumemi.android.code_check.data

import java.io.Serializable

data class SearchResultContents(
    val full_name: String,
    val owner: SearchResultOwnerContents,
    val language: String,
    val stargazers_count: Long,
    val watchers_count: Long,
    val forks_count: Long,
    val open_issues_count: Long,
) : Serializable

data class SearchResultOwnerContents(
    val avatar_url: String
)