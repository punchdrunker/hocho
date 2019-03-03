package tokyo.punchdrunker.hocho.transition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityPagerBinding
import androidx.core.app.ActivityCompat.setEnterSharedElementCallback
import androidx.core.app.SharedElementCallback
import androidx.viewpager.widget.ViewPager


class PagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPagerBinding
    private lateinit var adapter: PhotoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager)
        val selectedPosition = intent.getIntExtra(KEY_POSITION, 0)
        adapter = PhotoPagerAdapter(supportFragmentManager)
        binding.viewPager.also {
            it.setAdapter(adapter)
            it.setCurrentItem(selectedPosition)
            it.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    // updatePosition
                }
            })
        }
        prepareEnterTransition()
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

    companion object {
        fun createIntent(context: Context, position: Int): Intent {
            return Intent(context, PagerActivity::class.java).also {
                it.putExtra(KEY_POSITION, position)
            }
        }

        private const val KEY_POSITION = "KEY_POSITION"
    }
}
