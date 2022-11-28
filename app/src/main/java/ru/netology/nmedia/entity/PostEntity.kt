package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author:String,
    val published: String,
    val likes:Long,
    val liked:Boolean,
    val share: Long,
    val shareReal: Boolean,
    val content:String,
    val view: Long,
    val video: String? = null
){
    fun toDto()= Post(id,author,published,likes,liked,share,shareReal,content,view,video)
    companion object{
        fun fromDto(dto: Post) = PostEntity(dto.id,dto.author,dto.published,dto.likes,dto.liked,dto.share,dto.shareReal,dto.content,dto.view,dto.video)
    }
}




