package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.InMemoryPostRepository
import ru.netology.nmedia.repository.PostRepository
private val empty = Post(0,"","","",0,false,0,false,"",0, "https://www.youtube.com/")
class PostViewModel: ViewModel() {
    private  val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id:Long) = repository.likeById(id)
    fun shareById(id:Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun save() {
        edited.value?.let{
            repository.save(it)
        }
        edited.value = empty
    }
    fun edit(post: Post){
        edited.value = post
    }
    fun editContent(content: String){
        edited.value.let {
            val trimmed = content.trim()
            if (edited.value?.content == trimmed){
                return
            }
            edited.value = edited.value?.copy(content=trimmed)
        }
    }




}