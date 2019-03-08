package tokyo.punchdrunker.hocho.transition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import timber.log.Timber
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityToBinding

class ToActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToBinding
    var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_to)

        selectedPosition = intent.getIntExtra(KEY_POSITION, 0)
        binding.photo.setImageResource(PhotoStore.getImage(selectedPosition))
        prepareTransitionCallback()
    }

    override fun onDestroy() {
        PhotoStore.setCurrentPosition(this, 0)
        super.onDestroy()
    }

    private fun prepareTransitionCallback() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)
                Timber.w("EnterSharedElementCallback was called")
            }
        })
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                super.onMapSharedElements(names, sharedElements)
                Timber.w("ExitSharedElementCallback was called")
            }
        })
    }
    companion object {
        fun createIntent(context: Context, position: Int): Intent {
            return Intent(context, ToActivity::class.java).also {
                it.putExtra(KEY_POSITION, position)
            }
        }

        private const val KEY_POSITION = "KEY_POSITION"
    }
}
