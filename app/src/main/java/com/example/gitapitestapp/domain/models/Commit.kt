package com.example.gitapitestapp.domain.models

data class Commit(
    val author: Author,
    val comment_count: Int,
    val committer: Committer,
    val message: String,
    val url: String,
)