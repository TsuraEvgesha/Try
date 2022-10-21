package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityIntentHendlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHendlerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.send.setOnClickListener{
            val intent = Intent()
                .putExtra(Intent.EXTRA_TEXT,"Test text")
                .setAction(Intent.ACTION_SEND)
                .setType("text/plain")
            val createChooser =Intent.createChooser(intent,"Choose app")
            startActivity(createChooser)
        }
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
        }?: run {
            Snackbar.make(binding.root,"Content is empty",Snackbar.LENGTH_SHORT)
                .setAction(android.R.string.ok) {
                    finish()
                }
                .show()
        }
    }
}