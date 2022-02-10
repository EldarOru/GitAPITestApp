package com.example.gitapitestapp.domain.models

data class RepositoriesItem(
    val comments_url: String = "",
    val commits_url: String = "",
    val compare_url: String = "",
    val contents_url: String = "",
    val full_name: String = "",
    val git_commits_url: String = "",
    val git_refs_url: String = "",
    val git_tags_url: String = "",
    val git_url: String = "",
    val hooks_url: String = "",
    val html_url: String = "",
    val id: Int = -1,
    val name: String = "",
    val node_id: String = "",
    val notifications_url: String = "",
    val owner: Owner = Owner(),
    val url: String = ""
)