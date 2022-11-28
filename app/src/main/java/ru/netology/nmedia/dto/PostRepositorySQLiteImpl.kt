package ru.netology.nmedia.dto
import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.PostEntity

import ru.netology.nmedia.repository.PostRepository

class PostRepositorySQLiteImpl (
    private val dao: PostDao
) : PostRepository {
    private var posts = emptyList<Post>()

    override fun getAll() = Transformations.map(dao.getAll()){ list ->
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