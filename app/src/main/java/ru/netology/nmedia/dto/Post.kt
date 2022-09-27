package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author:String,
    val authorAvatar: String,
    val published: String,
    val likes:Long,
    val liked:Boolean,
    val share: Long,
    val shareReal: Boolean,
    val content:String,
    val view: Long,
)


