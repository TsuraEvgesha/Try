package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostDaoImpl (private  val db: SQLiteDatabase): PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE_REAL} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEW} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT
          
        );
        """.trimIndent()
    }
    object PostColumns{
        const val TABLE ="posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED = "liked"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_SHARE = "share"
        const val COLUMN_SHARE_REAL = "shareReal"
        const val COLUMN_VIEW = "view"
        const val COLUMN_VIDEO = "video"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED,
            COLUMN_LIKES,
            COLUMN_SHARE,
            COLUMN_SHARE_REAL,
            COLUMN_VIEW,
            COLUMN_VIDEO
        )
    }

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)


    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()){
                posts.add(map(it))
            }
        }
        return posts
    }
    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
                likes = likes + CASE WHEN liked THEN -1 ELSE 1 END,
                liked = CASE WHEN liked THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )

    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID}=?",
            arrayOf(id.toString())
        )

    }

    override fun shareById(id: Long) {

        db.execSQL(
            """
           UPDATE posts SET
                share = likes + CASE WHEN shareReal THEN -1 ELSE 1 END,
                shareReal = CASE WHEN shareReal THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )

    }

    override fun save(post: Post):Post {
        val values = ContentValues().apply {
            if(post.id != 0L){
                put(PostColumns.COLUMN_ID,post.id)
            }
            put(PostColumns.COLUMN_AUTHOR,"ME")
            put(PostColumns.COLUMN_CONTENT,post.content)
            put(PostColumns.COLUMN_PUBLISHED,"now")
            put(PostColumns.COLUMN_VIDEO, post.video)
        }
        val id = db.replace(PostColumns.TABLE,null,values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }


    }
    private fun map(cursor: Cursor):Post{
        with(cursor){
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                liked = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED)) !=0,
                likes = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                share = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE)),
                shareReal = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE_REAL)) !=0,
                view = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_VIEW)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO))

            )
        }
    }



}