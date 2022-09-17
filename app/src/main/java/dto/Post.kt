package dto

data class Post (
    val id: Long =0,
    val author:String,
    val authorAvatar: String,
    val published: String,
    val likes:Int,
    var liked:Boolean,
    val sare: Long,
    val content:String,
    val view: Long,
)


