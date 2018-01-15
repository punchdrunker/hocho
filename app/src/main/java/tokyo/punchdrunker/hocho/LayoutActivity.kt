package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tokyo.punchdrunker.hocho.databinding.ActivityLayoutBinding

class LayoutActivity : AppCompatActivity() {
    private val binding: ActivityLayoutBinding by lazy {
        DataBindingUtil.setContentView<ActivityLayoutBinding>(this, R.layout.activity_layout)
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
        binding.toolbarTitle.text = getString(R.string.layout_title)
    }
}