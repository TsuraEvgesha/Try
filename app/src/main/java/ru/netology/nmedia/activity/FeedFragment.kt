package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import viewmodel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer= ::requireParentFragment
    )
    companion object {
        private  const val TEXT_KEY="TEXT_KEY"
        var Bundle.textArg: String?
        set(value) = putString(TEXT_KEY, value)
        get() = getString(TEXT_KEY)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
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
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        binding.create.setOnClickListener{
            findNavController().navigate(R.id.action_feedFragment2_to_newPostFragment2)

        }
        return binding.root
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







