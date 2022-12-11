package ru.netology.nmedia.repository


import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun save(post: Post)
    fun likeById(id: Long):Post
    fun sharedById(id: Long):Post
    fun dislikeById(id:Long):Post
    fun removeById(id: Long)


}