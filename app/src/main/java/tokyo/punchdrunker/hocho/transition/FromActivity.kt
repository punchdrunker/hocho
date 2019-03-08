package tokyo.punchdrunker.hocho.transition

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import timber.log.Timber
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityFromBinding

class FromActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFromBinding
    // Go bold
    private var fromFragment: FromFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_from)
        setupToolbar()

        PhotoStore.setCurrentPosition(this, 0)

        fromFragment = FromFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fromFragment!!)
                .commitAllowingStateLoss()
        prepareTransitionCallback()
    }

    override fun onDestroy() {
        fromFragment = null
        super.onDestroy()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarTitle.text = getString(R.string.transition)
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
                Timber.w("ExitSharedElementCallback was called: position %s", PhotoStore.getCurrentPosition(this@FromActivity))
                // fromFragment.scrollToCurrentPosition()
                val viewHolder = fromFragment?.getViewHolder(PhotoStore.getCurrentPosition(this@FromActivity))
                val itemView = viewHolder?.itemView ?: return
                val photoView = itemView.findViewById<ImageView>(R.id.card_photo)
                sharedElements[names[0]] = photoView
            }
        })
    }
}
