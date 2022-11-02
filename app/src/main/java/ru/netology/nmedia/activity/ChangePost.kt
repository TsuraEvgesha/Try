package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityChangePostBinding


class ChangePostActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChangePostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        if (extras != null){
            binding.contentText.setText(extras.getString(RESULT_KEY_EDIT))
        }
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            val intent=Intent()
            val text = binding.contentText.text
            if (text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else{
                val content=text.toString()
                intent.putExtra(RESULT_KEY,content)
                setResult(Activity.RESULT_OK,intent)
            }
            finish()


        }
    }
    private companion object {
        private const val RESULT_KEY = "postNewContent"
        private const val RESULT_KEY_EDIT = "postEditContent"
    }
}




