package ru.netology.nmedia.activity


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel


class
NewPostFragment: Fragment() {
//    companion object {
//        var Bundle.textArg: String? by StringArg
//    }

    val args by navArgs<NewPostFragmentArgs>()
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        binding.contentText.setText(args.content)
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            viewModel.editContent(binding.contentText.text.toString())
            viewModel.save()
            AndroidUtils.hideKeyboard(requireView())

            findNavController().navigateUp()
        }
        return binding.root
    }
}



