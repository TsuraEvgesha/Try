package dto

data class Post (
    val id: Long =0,
    val author:String,
    val authorAvatar: String,
    val published: String,
    var likes:Long,
    var liked:Boolean,
    var share: Long,
    var sareReal:Boolean,
    val content:String,
    val view: Long,
)


