package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ActivityLifecycleBinding

class LifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLifecycleBinding>(this, R.layout.activity_lifecycle).also {
            it.activity = this
        }
        Timber.v("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.v("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.v("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.v("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.v("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.v("onRestart")
    }
}
