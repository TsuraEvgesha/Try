package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding


class NewPostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val binding = FragmentNewPostBinding.inflate(
            inflater,
            container,
            false
        )
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            val intent= Intent()
            if (TextUtils.isEmpty(binding.contentText.text)) {
                activity?.setResult(Activity.RESULT_CANCELED,intent)
               } else{
                val content=binding.contentText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT,content)
                activity?.setResult(Activity.RESULT_OK,intent)
            }
            findNavController().navigateUp()


        }
        return binding.root
    }

    }

