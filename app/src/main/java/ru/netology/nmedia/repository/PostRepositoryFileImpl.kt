package ru.netology.nmedia.repository


import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import java.io.IOException


class PostRepositoryFileImpl(private val dao:PostDao): PostRepository {
    override val data = dao.getAll().map(List<PostEntity>::toDto)

    override suspend fun getAll() {
        try {
            val response = PostsApi.service.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            dao.insert(body.toEntity())
        } catch (e:IOException){
            throw NetworkError
        } catch (e:Exception){
            throw UnknownError
        }
    }
    override suspend fun save(post: Post):Post =PostApi.service.save(post)
    override suspend fun removeById(id: Long) =PostApi.service.removeById(id)
    override suspend fun likeById(id: Long):Post =PostApi.service.likeById(id)


//    override suspend fun sharedById(id: Long):Post = PostApi.service.sharedById()


}