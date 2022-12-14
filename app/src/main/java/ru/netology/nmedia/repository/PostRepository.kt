package ru.netology.nmedia.repository


import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): List<Post>
    fun save(post: Post,callback: SaveOrRemovePosts)
    fun likeById(id: Long, callback: PostsCallback<Post>)
    fun sharedById(id: Long):Post
    fun dislikeById(id:Long, callback: PostsCallback<Post>)
    fun removeById(id: Long,callback: SaveOrRemovePosts)
    fun getAllAsync(callback:GetAllCallback)
    interface GetAllCallback {
        fun onSuccess(posts: List<Post>){}
        fun onError(e:Exception){}
    }
    interface PostsCallback<T> {
        fun onSuccess(posts: T)
        fun onError(e: Exception)
    }
    interface SaveOrRemovePosts{
        fun onSuccess()
        fun onError(e: Exception)
    }


}