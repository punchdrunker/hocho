package tokyo.punchdrunker.hocho

import android.net.Uri
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

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TransitionFragment.newInstance())
                .commitAllowingStateLoss()
    }
}
