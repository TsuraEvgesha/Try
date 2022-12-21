package ru.netology.nmedia.viewmodelimport android.app.Applicationimport androidx.lifecycle.AndroidViewModelimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport ru.netology.nmedia.dto.Postimport ru.netology.nmedia.repository.PostRepositoryimport ru.netology.nmedia.repository.PostRepositoryFileImplimport ru.netology.nmedia.util.SingleLiveEventimport java.io.IOExceptionimport kotlin.concurrent.threadprivate val empty = Post(0,"Me","","",0,false,0, null)class PostViewModel(application: Application) : AndroidViewModel(application) {private val repository: PostRepository = PostRepositoryFileImpl()    private val _data = MutableLiveData(FeedModel())    val data: LiveData<FeedModel>    get() = _data    private val edited = MutableLiveData(empty)    private val _postCreated = SingleLiveEvent<Unit>()    val postCreated: LiveData<Unit>    get() = _postCreated    init {        loadPosts()    }    fun loadPosts() {            _data.postValue(FeedModel(loading = true))        repository.getAllAsync(object :PostRepository.Callback<List<Post>>{            override fun onSuccess(posts: List<Post>) {                _data.postValue(FeedModel(posts = posts, empty = posts.isEmpty()))            }            override fun onError(e: Exception) {                _data.postValue(FeedModel(error = true))            }        })    }    fun likeById(id:Long){        var oldPosts = _data.value?.posts.orEmpty()        repository.likeById(id,object :PostRepository.Callback<Post>{            override fun onSuccess(posts: Post) {                oldPosts = oldPosts.map{                    if (it.id != id) it else it.copy(                        likedByMe = posts.likedByMe,                        likes = posts.likes)                }                _data.postValue(FeedModel(posts = oldPosts))            }            override fun onError(e: Exception) {                _data.postValue(FeedModel(error = true))            }        })    }    fun dislikeById(id:Long) {        var oldPosts = _data.value?.posts.orEmpty()        repository.likeById(id,object :PostRepository.Callback<Post>{            override fun onSuccess(posts: Post) {                oldPosts = oldPosts.map{                    if (it.id != id) it else it.copy(                        likedByMe = posts.likedByMe,                        likes = posts.likes)                }                _data.postValue(FeedModel(posts = oldPosts))            }            override fun onError(e: Exception) {                _data.postValue(FeedModel(error = true))            }        })    }        fun sharedById(id: Long) {            thread {                val old = _data.value?.posts.orEmpty()                val posts = _data.value?.posts.orEmpty()                posts.map {                    if (it.id != id) it else it.copy()                }                try {                    repository.sharedById(id)                } catch (e: IOException) {                    _data.postValue(_data.value?.copy(posts = old))                }            }        }        fun refresh() {            loadPosts()        }        fun removeById(id: Long) {            repository.removeById(id,object :PostRepository.SaveOrRemovePosts{                override fun onSuccess() {                    _data.postValue(                        _data.value?.copy(posts = data.value?.posts.orEmpty()                            .filter { it.id!=id })                    )                }                override fun onError(e: Exception) {                    _data.postValue(FeedModel(error = true))                }            })        }        fun save() {            edited.value?.let {                    repository.save(it, object:PostRepository.SaveOrRemovePosts{                        override fun onSuccess() {                            _postCreated.postValue(Unit)                        }                        override fun onError(e: Exception) {                            _data.postValue(FeedModel(error = true))                        }                    } )                }            }        fun edit(post: Post) {            edited.value = post        }        fun editContent(content: String) {            edited.value.let {                val trimmed = content.trim()                if (edited.value?.content == trimmed) {                    return                }                edited.value = edited.value?.copy(content = trimmed)            }        }    }