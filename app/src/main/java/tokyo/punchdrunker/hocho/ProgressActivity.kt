package tokyo.punchdrunker.hocho

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil

import tokyo.punchdrunker.hocho.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private val binding: ActivityProgressBinding by lazy {
        DataBindingUtil.setContentView<ActivityProgressBinding>(this, R.layout.activity_progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply{
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
        binding.toolbarTitle.text = getString(R.string.progress_title)
    }
}
