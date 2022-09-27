package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository: PostRepository {
    private var post = Post(
        0,
        "Нетология. Университет интернет-профессий",
        "",
        "21 мая в 18:36",
        10,
        false,
        5,
        false,
        "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам:от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целится выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http//netolo.gy/fyb",
        0
    )
    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(
            liked = !post.liked,
            likes = if(post.liked) post.likes -1 else post.likes +1
        )
        data.value=post
    }

    override fun share() {
        post = post.copy(
            share = (post.share + 1)
        )
        data.value=post
    }
}