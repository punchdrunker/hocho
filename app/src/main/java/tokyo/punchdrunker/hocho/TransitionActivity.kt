package tokyo.punchdrunker.hocho

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.databinding.ActivityTransitionBinding

class TransitionActivity : AppCompatActivity(), TransitionFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
    }

    private lateinit var binding: ActivityTransitionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition)
        setupToolbar()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TransitionFragment.newInstance())
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
