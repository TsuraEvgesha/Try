package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        run {
//            val preferences= getPreferences(Context.MODE_PRIVATE)
//            preferences.edit().apply {
//                putString("key","value")
//                commit()
//            }
//        }
//        run {
//            getPreferences(Context.MODE_PRIVATE)
//                .getString("key","no value")?.let {
//                    Snackbar.make(binding.root,it,BaseTransientBottomBar.LENGTH_INDEFINITE)
//                        .show()
//                }
//        }
        val viewModel: PostViewModel by viewModels()
        val newPostLauncher = registerForActivityResult(NewPostActivityContract()){text ->
            text?: return@registerForActivityResult
            viewModel.editContent(text)
            viewModel.save()
        }


        val changePostLauncher= registerForActivityResult(ChangePostActivityContract()){post ->
            post?: return@registerForActivityResult

            viewModel.editContent(post)
            viewModel.save()
        }

        val adapter = PostsAdapter (object : PostListener {

            override fun onEdit(post: Post) {
                changePostLauncher.launch(post.content)
                viewModel.edit(post)

            }
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action=Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,post.content)
                    type="text/plan"
                }
                val shareIntent = Intent.createChooser(intent,getString(R.string.share))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun onRemote(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPlayVideo(post: Post) {
                Intent(Intent.ACTION_VIEW,
                    Uri.parse(post.video)).apply {
                    if (resolveActivity(packageManager) != null){
                        startActivity(this)

                    }
                }
            }
        }
        )



        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.create.setOnClickListener{
            newPostLauncher.launch()
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







