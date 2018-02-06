package tokyo.punchdrunker.hocho

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHelpBinding.inflate(inflater, container!!, false)
        Timber.v("onCreateView")
        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Timber.v("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("onCreate")
    }

    override fun onStart() {
        super.onStart()
        Timber.v("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.v("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.v("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.v("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.v("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.v("onDestroy")
    }

    companion object {
        fun newInstance(): HelpFragment = HelpFragment()
    }
}