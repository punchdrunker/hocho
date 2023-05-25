package tokyo.punchdrunker.hocho

import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions
import com.google.android.material.snackbar.Snackbar
import tokyo.punchdrunker.hocho.databinding.ActivityLayoutBinding


class LayoutActivity : AppCompatActivity() {
    private val binding: ActivityLayoutBinding by lazy {
        DataBindingUtil.setContentView<ActivityLayoutBinding>(this, R.layout.activity_layout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        binding.componentInclude.button.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.snackbar_message), Snackbar.LENGTH_SHORT).show()
        }
        binding.user = User("punchdrunker", "engineer")
        binding.fab.setOnClickListener {
            Snackbar.make(it, getString(R.string.fab_message), Snackbar.LENGTH_SHORT).show()
        }

        val sourceImage = BitmapFactory.decodeResource(resources, R.drawable.img_sea)


        DynamicColors.applyToActivityIfAvailable(
            this,
            DynamicColorsOptions.Builder()
                .setContentBasedSource(sourceImage)
                .build()
        )
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
        binding.toolbarTitle.text = getString(R.string.layout_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var result = true

        when (id) {
            android.R.id.home -> finish()
            else -> result = super.onOptionsItemSelected(item)
        }

        return result
    }

    data class User constructor(val name: String, val title: String)
}
