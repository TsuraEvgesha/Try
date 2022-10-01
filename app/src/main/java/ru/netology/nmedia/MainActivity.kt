package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter ({
            viewModel.likeById(it.id)},
            {viewModel.shareById(it.id)
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
            }

        }
    }

    fun counter(item: Long): String {
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






