package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class ChangePostActivityContract: ActivityResultContract<String, String?>(){
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context,ChangePostFragment::class.java).putExtra(RESULT_KEY_EDIT,input)
    }
    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(RESULT_KEY)
        } else null
    private companion object {
        private const val RESULT_KEY = "postNewContent"
        private const val RESULT_KEY_EDIT = "postEditContent"
    }

}