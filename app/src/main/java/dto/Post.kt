package dto

data class Post (
    val id: Long =0,
    val author:String,
    val authorAvatar: String,
    val published: String,
    var likes:Long,
    var liked:Boolean,
    var share: Long,
    val content:String,
    val view: Long,
)


