package ru.sixsixsix.lab1.model

data class Post(
    val id: Int,
    val text: String,
    val imageUrl: String?,
    var likes: Int,
    val comments: Int,
    var isLiked: Boolean
)