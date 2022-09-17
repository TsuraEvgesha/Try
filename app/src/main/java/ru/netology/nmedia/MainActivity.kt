package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dto.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(0,"Нетология. Университет интернет-профессий","","21 мая в 18:36", 10,true,5,"Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растем сами и помогаем расти студентам:от новичков до уверенных профессионалов. Но самое важное остается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целится выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http//netolo.gy/fyb",5)
        with(binding){
        published.text=post.published
        author.text=post.author
        content.text=post.content
        likes.setOnClickListener {
            post.liked=!post.liked
            likes.setImageResource(
                if(post.liked) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_outline_favorite_border_24
                }

            )
        }
        }


    }
}
