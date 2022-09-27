package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel by viewModels<PostViewModel>()
        viewModel.data.observe(this){ post ->
            with(binding) {
                published.text = post.published
                author.text = post.author
                content.text = post.content
                numberOfLikes.text = counter(post.likes)
                numberOfShare.text= counter(post.share)
                numberOfVisit.text=counter(post.view)
                val likeImage = if (post.liked) {
                    R.drawable.ic_baseline_favorite_24
                } else {
                    R.drawable.ic_outline_favorite_border_24
                }
                likes.setImageResource(likeImage)
                numberOfLikes.text = counter(post.likes)
                val shareImage = if (post.shareReal) R.drawable.ic_baseline_sharetrue_24
                else R.drawable.ic_baseline_share_24
                share.setImageResource(shareImage)
                numberOfShare.text=counter(post.share)


            }
            binding.likes.setOnClickListener {
                viewModel.like()

            }

            binding.share.setOnClickListener{
                viewModel.share()

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




