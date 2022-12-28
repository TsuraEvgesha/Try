package ru.netology.nmedia.repository


import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: LiveData<List<Post>>
    suspend fun getAll(): List<Post>
    suspend fun save(post: Post): Post
    suspend fun likeById(id: Long)
//    fun sharedById(id: Long):Post
//    suspend fun dislikeById(id:Long, callback: Callback<Post>)
    suspend fun removeById(id: Long)
//    suspend fun getAllAsync(callback:Callback<List<Post>>)
//    interface Callback<T> {
//        fun onSuccess(posts: T){}
//        fun onError(e:Exception, code:Int){}
//    }
}