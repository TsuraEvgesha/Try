package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: Long,
    val likedByMe: Boolean,
    val likes: Int = 0,

    ) {
    val view: Long = 0
    val video: String? = null
    val share: Long=0
    val shareReal: Boolean=false
}