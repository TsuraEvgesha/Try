package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.let {
            if (it.action!= Intent.ACTION_SEND){
                return@let
            }
            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text?.isNotBlank()!= true){
                return@let
            }
            intent.removeExtra(Intent.EXTRA_TEXT)
            findNavController(R.id.nav_host_fragment_container)
                .navigate(
                    R.id.action_feedFragment2_to_newPostFragment2,
                    Bundle().apply {
                        textArg = text
                    }
                )

        }
        checkGoogleApiAvailability()
    }
    private fun checkGoogleApiAvailability(){
        with(GoogleApiAvailability.getInstance()){
            val code = isGooglePlayServicesAvailable(this@AppActivity)
            if (code == ConnectionResult.SUCCESS){
                return@with
            }
            if (isUserResolvableError(code)){
                getErrorDialog(this@AppActivity,code,9000)?.show()
            }

        }
    }
}

