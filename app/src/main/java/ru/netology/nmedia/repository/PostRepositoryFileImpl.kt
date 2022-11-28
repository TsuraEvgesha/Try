package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity


class PostRepositoryFileImpl (
    private val dao: PostDao,
) : PostRepository {
//    private val gson = Gson()
//    private val type = TypeToken.getParameterized(List::class.java, PostEntity::class.java).type
//    private val filename = "posts.json"

    override fun getAll() = Transformations.map(dao.getAll()){list ->
        list.map{
            it.toDto()
        }
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))

    }



}