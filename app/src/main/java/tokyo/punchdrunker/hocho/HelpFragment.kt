package tokyo.punchdrunker.hocho

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.databinding.FragmentHelpBinding

class HelpFragment: Fragment() {
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHelpBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    companion object {
        fun newInstance(): HelpFragment = HelpFragment()
    }
}