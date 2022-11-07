package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
        val intent= Intent()
        val extras = intent.extras
        if (extras != null){
            binding.contentText.setText(extras.getString(RESULT_KEY_EDIT))
        }
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            val text = binding.contentText.text
            if (text.isNullOrBlank()) {
                activity?.setResult(Activity.RESULT_CANCELED,intent)
               } else{
                val content=text.toString()
                intent.putExtra(RESULT_KEY,content)
                activity?.setResult(Activity.RESULT_OK,intent)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
    private companion object {
        private const val RESULT_KEY = "postNewContent"
        private const val RESULT_KEY_EDIT = "postEditContent"
    }
}



