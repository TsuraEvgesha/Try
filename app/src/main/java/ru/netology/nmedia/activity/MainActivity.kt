package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter (object: OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun onRemote(post: Post) {
                viewModel.removeById(post.id)
            }
        }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this){post ->
            if (post.id == 0L){
                return@observe
            }
            with(binding.contentText) {
                requestFocus()
                setText(post.content)
            }
        }
        binding.buttonCancelEdit.setOnClickListener {
            binding.buttonCancelEdit.visibility = GONE
            with(binding.contentText) {
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
        binding.contentText.setOnClickListener{
            binding.buttonCancelEdit.visibility= VISIBLE
        }

        binding.save.setOnClickListener {
            with(binding.contentText) {
                val text = text.toString()
                if (text.isBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can not be empty",
                        Toast.LENGTH_SHORT

                    ).show()

                    return@setOnClickListener
                }
                viewModel.editContent(text)
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)

            }


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







