package ru.netology.nmedia.repository


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.netology.nmedia.api.PostApiServiceHolder
import ru.netology.nmedia.dto.Post



class PostRepositoryFileImpl: PostRepository {

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostApiServiceHolder.service.getPosts()
            .enqueue(object : Callback<List<Post>> {
                override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()),response.code())
                        return
                    }
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }

                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    callback.onError(RuntimeException(t), 404)
                }
            })
    }
    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.save(post)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()),response.code())
                        return
                    }
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t),404)
                }
            })
    }
    override fun removeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.delete(id)
            .enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful) {
                        callback.onError(RuntimeException(response.message()),response.code())
                        return
                    }
                    callback.onSuccess(response.body() ?: throw RuntimeException("body is null"))
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t),404)
                }
            })
    }
    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostApiServiceHolder.service.likeById(id)
            .enqueue(object :Callback<Post>{
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (!response.isSuccessful){
                        callback.onError(RuntimeException(response.message()),response.code())
                        return
                    }
                    callback.onSuccess(response.body()?:throw RuntimeException("body is null"))
                }
                override fun onFailure(call: Call<Post>, t: Throwable) {
                    callback.onError(RuntimeException(t),404)
                }


    })
    }


    override fun dislikeById (id: Long, callback: PostRepository.Callback<Post>) {
                PostApiServiceHolder.service.dislikeById(id)
                    .enqueue(object :Callback<Post>{
                        override fun onResponse(call: Call<Post>, response: Response<Post>) {
                            if (!response.isSuccessful){
                                callback.onError(RuntimeException(response.message()),response.code())
                                return
                            }
                            callback.onSuccess(response.body()?:throw RuntimeException("body is null"))
                        }
                        override fun onFailure(call: Call<Post>, t: Throwable) {
                            callback.onError(RuntimeException(t),404)
                        }


    })
    }

//    override fun sharedById(id: Long):Post {
//        val request: Request = Request.Builder()
//            .post("".toRequestBody())
//            .url("${BASE_URL}/api/posts/$id/likes")
//            .build()
//        return client.newCall(request)
//            .execute()
//            .let { it.body?.string() ?: throw RuntimeException("error") }
//            .let {
//                gson.fromJson(it, Post::class.java)
//            }
//    }


}