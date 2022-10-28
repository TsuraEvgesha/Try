package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.netology.nmedia.databinding.ActivityNewPostBinding


class NewPostActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            val text = binding.contentText.text
            if (text.isBlank()) {
                Toast.makeText(
                    this,
                    "Content can not be empty",
                    Toast.LENGTH_SHORT
                ).show()} else{
                val content=binding.contentText.text.toString()
                val result = Intent().putExtra(Intent.EXTRA_TEXT,content)
                setResult(Activity.RESULT_OK,result)
                }
            finish()


            }

        }
    }
class ChangePostActivity: AppCompatActivity(){
    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras
        if (extras != null){
            binding.contentText.setText(extras.getString("Post edit content"))
        }
        binding.contentText.requestFocus()
        binding.save.setOnClickListener{
            val text = binding.contentText.text
            if (text.isBlank()) {
                Toast.makeText(
                    this,
                    "Content can not be empty",
                    Toast.LENGTH_SHORT
                ).show()} else{
                val content=binding.contentText.text.toString()
                val result = Intent().putExtra(Intent.EXTRA_TEXT,content)
                setResult(Activity.RESULT_OK,result)
            }
            finish()


        }
    }

}
