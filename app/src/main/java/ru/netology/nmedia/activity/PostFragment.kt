package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: PostViewModel by viewModels(
        ownerProducer= ::requireParentFragment
    )
    private val args by navArgs<PostFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding= FragmentPostBinding.inflate(inflater,container, false)
        val viewHolder= PostsAdapter.PostViewHolder(binding.cardPost, object : PostListener {

            override fun onEdit(post: Post) {
                val action = PostFragmentDirections.actionPostFragmentToNewPostFragment2(post.content)
                findNavController().navigate(action)
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plan"
                }
                val shareIntent = Intent.createChooser(intent, getString(R.string.share))
                startActivity(shareIntent)
                viewModel.shareById(post.id)
            }

            override fun onRemote(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onPlayVideo(post: Post) {
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(post.video)
                ).apply {
                    if (requireContext().packageManager != null) {
                        startActivity(this)
                    }
                }
            }

            override fun onPost(post: Post) {
                val action=FeedFragmentDirections.actionFeedFragment2ToPostFragment(post.id.toInt())
                findNavController().navigate(action)
            }
        }
        )
        viewModel.data.observe(viewLifecycleOwner){posts->
            val post = posts.find { it.id == args.postId.toLong() }?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(post)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}