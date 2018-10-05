package tokyo.punchdrunker.hocho

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_progress.*

import tokyo.punchdrunker.hocho.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private val binding: ActivityProgressBinding by lazy {
        DataBindingUtil.setContentView<ActivityProgressBinding>(this, R.layout.activity_progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        binding.activity = this
        binding.customButton.setOnClickListener { button ->
            (button as CustomButton).toggle()
        }
    }

    fun setProgress0(view: View) {
        progress3.progress = 0
    }

    fun setProgress50(view: View) {
        progress3.progress = 50
    }

    fun setProgress100(view: View) {
        progress3.progress = 100
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
