package viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository

class PostViewModel: ViewModel() {
    private  val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    fun likeById(id:Long) = repository.likeById(id)
    fun shareById(id:Long) = repository.shareById(id)

}