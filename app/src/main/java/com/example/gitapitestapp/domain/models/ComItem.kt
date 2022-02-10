package com.example.gitapitestapp.domain.models

data class ComItem(
    val sha: String,
    val url: String,
    val commit: Commit,
    val parents: ArrayList<Parent>
)