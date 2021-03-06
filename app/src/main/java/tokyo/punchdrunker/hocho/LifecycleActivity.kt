package tokyo.punchdrunker.hocho

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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

    fun onOpenWeb(view: View) {
        view.context.apply {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://droidkaigi.jp"))
            startActivity(intent)
        }
    }

    fun onSearch(view: View) {
        view.context.apply {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, "droidkaigi")
            }
            startActivity(intent)
        }
    }
}
