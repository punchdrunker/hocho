package tokyo.punchdrunker.hocho.progress

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.applyTopSystemBarInsets
import tokyo.punchdrunker.hocho.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private val binding: ActivityProgressBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setupEdgeToEdge()
        setupToolbar()

        binding.activity = this
        binding.customButton.setOnClickListener { button ->
            (button as CustomButton).toggle()
        }
    }

    private fun setupEdgeToEdge() {
        binding.toolbar.applyTopSystemBarInsets()
    }

    fun setProgress0() {
        binding.progress3.progress = 0
    }

    fun setProgress50() {
        binding.progress3.progress = 50
    }

    fun setProgress100() {
        binding.progress3.progress = 100
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
        binding.toolbarTitle.text = getString(R.string.progress_title)
    }
}
