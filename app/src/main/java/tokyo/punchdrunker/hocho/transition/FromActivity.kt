package tokyo.punchdrunker.hocho.transition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityFromBinding

class FromActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFromBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_from)
        setupToolbar()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FromFragment.newInstance())
                .commitAllowingStateLoss()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarTitle.text = getString(R.string.transition)
    }
}
