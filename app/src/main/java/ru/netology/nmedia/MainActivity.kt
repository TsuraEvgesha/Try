package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dto.Post
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
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
        with(binding) {
            published.text = post.published
            author.text = post.author
            content.text = post.content
            numberOfLikes.text = counter(post.likes)
            numberOfShare.text= counter(post.share)
            numberOfVisit.text=counter(post.view)
            likes.setOnClickListener {
                if (post.liked) post.likes-- else post.likes++
                post.liked = !post.liked
                numberOfLikes.text = counter(post.likes)
                likes.setImageResource(
                    if (post.liked) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_outline_favorite_border_24
                    }
                )

            }
            share.setOnClickListener{
                post.share++
                post.sareReal=!post.sareReal
                numberOfShare.text=counter(post.share)
                share.setImageResource(
                    if (post.sareReal){
                        R.drawable.ic_baseline_sharetrue_24
                    } else{
                        R.drawable.ic_baseline_sharetrue_24
                    }
                )
            }

        }

    }



    private fun counter(item: Long): String {

        return when (item) {

            in 1000..1099 -> {
                val num = numberToInt(item / 1000.0)
                (num + "K") }
            in 1100..9999 -> {
                val num = numberToInt(item / 1000.0)
                (num + "K") }
            in 10_000..999_999 -> {
                ((item / 1000).toString() + "K") }
            in 1_000_000..1_000_000_000 -> {
                val num = numberToInt(item / 1_000_000.0)
                (num + "M") }
            else -> item.toString()
        }
    }

    private fun numberToInt (number: Double): String {
        val demFormat = DecimalFormat("#.#")
        demFormat.roundingMode = RoundingMode.FLOOR
        return demFormat.format(number).toString()
    }



}




