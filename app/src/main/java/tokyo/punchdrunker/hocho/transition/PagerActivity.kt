package tokyo.punchdrunker.hocho.transition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityPagerBinding

class PagerActivity : AppCompatActivity() {
    private var initialPosition = 0
    private var useTransition = true
    private lateinit var binding: ActivityPagerBinding
    private lateinit var adapter: PhotoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialPosition = intent.getIntExtra(KEY_POSITION, 0)
        useTransition = intent.getBooleanExtra(KEY_USE_TRANSITION, true)

        adapter = PhotoPagerAdapter(supportFragmentManager)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pager)
        binding.viewPager.also {
            it.adapter = adapter
            it.currentItem = initialPosition
            it.addOnPageChangeListener(
                object : ViewPager.SimpleOnPageChangeListener() {
                    override fun onPageSelected(position: Int) {
                        PhotoStore.setCurrentPosition(this@PagerActivity, position)
                    }
                },
            )
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Backボタン検知する
                    if (!useTransition) {
                        finishViewer()
                    }
                }
            }
        onBackPressedDispatcher.addCallback(callback)
        prepareEnterTransition()
    }

    private fun prepareEnterTransition() {
        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>,
                ) {
                    val detailImageView =
                        adapter.getCurrentFragment().view?.findViewById<ImageView>(R.id.photo)
                    if (detailImageView != null) {
                        sharedElements[names[0]] = detailImageView
                    }
                }
            },
        )
    }

    private fun finishViewer() {
        if (initialPosition == PhotoStore.getCurrentPosition(this)) {
            finishAfterTransition()
        } else {
            finish()
        }
    }

    companion object {
        fun createIntent(
            context: Context,
            position: Int,
            useTransition: Boolean = true,
        ): Intent =
            Intent(context, PagerActivity::class.java).also {
                it.putExtra(KEY_POSITION, position)
                it.putExtra(KEY_USE_TRANSITION, useTransition)
            }

        private const val KEY_POSITION = "KEY_POSITION"
        private const val KEY_USE_TRANSITION = "KEY_USE_TRANSITION"
    }
}
