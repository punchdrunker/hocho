package tokyo.punchdrunker.dynamic.gallery

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tokyo.punchdrunker.dynamic.gallery.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    private val binding: ActivityGalleryBinding by lazy {
        DataBindingUtil.setContentView<ActivityGalleryBinding>(this, R.layout.activity_gallery)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = getString(R.string.title_gallery)
    }
}