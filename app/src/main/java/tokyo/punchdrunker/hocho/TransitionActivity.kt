package tokyo.punchdrunker.hocho

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity() {
    private val binding: ActivityTransitionBinding by lazy {
        DataBindingUtil.setContentView<ActivityTransitionBinding>(this, R.layout.activity_transition)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
            exitTransition = Fade()
        }

        binding.activity = this
    }

    fun openActivity(view: View) {
        val intent = Intent(this, TransitionDetailActivity::class.java)
        startActivity(intent)
    }

    fun openActivityWithTransition(view: View) {
        val intent = Intent(this, TransitionDetailActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}
