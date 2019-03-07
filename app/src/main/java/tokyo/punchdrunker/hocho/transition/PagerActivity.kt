package tokyo.punchdrunker.hocho.transition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityPagerBinding


class PagerActivity : AppCompatActivity() {
    private var initialPosition = 0
    private lateinit var binding: ActivityPagerBinding
    private lateinit var adapter: PhotoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialPosition = intent.getIntExtra(KEY_POSITION, 0)
        PhotoStore.setCurrentPosition(this, initialPosition)

        adapter = PhotoPagerAdapter(supportFragmentManager)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager)
        binding.viewPager.also {
            it.adapter = adapter
            it.currentItem = initialPosition
            it.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    PhotoStore.setCurrentPosition(this@PagerActivity, position)
                }
            })
        }
        prepareEnterTransition()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        // Backボタン検知する
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            finishViewer()
            return false
        }
        return super.dispatchKeyEvent(event)
    }

    private fun prepareEnterTransition() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                val detailImageView = adapter.getCurrentFragment().view?.findViewById<ImageView>(R.id.photo)
                if (detailImageView != null) {
                    sharedElements[getString(R.string.shared_element)] = detailImageView
                }
            }
        })
    }

    private fun finishViewer() {
        if (initialPosition == PhotoStore.getCurrentPosition(this)) {
            finishAfterTransition()
        } else {
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context, position: Int): Intent {
            return Intent(context, PagerActivity::class.java).also {
                it.putExtra(KEY_POSITION, position)
            }
        }

        private const val KEY_POSITION = "KEY_POSITION"
    }
}
