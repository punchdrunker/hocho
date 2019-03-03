package tokyo.punchdrunker.hocho.transition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.ActivityToBinding

class ToActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToBinding
    var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_to)

        selectedPosition = intent.getIntExtra(KEY_POSITION, 0)
        binding.photo.setImageResource(PhotoStore.getImage(selectedPosition))
    }

    companion object {
        fun createIntent(context: Context, position: Int): Intent {
            return Intent(context, ToActivity::class.java).also {
                it.putExtra(KEY_POSITION, position)
            }
        }

        private const val KEY_POSITION = "KEY_POSITION"
    }
}
