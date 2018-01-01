package tokyo.punchdrunker.hocho

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.databinding.FragmentBookmarksBinding

class BookmarksFragment: Fragment() {
    private lateinit var binding: FragmentBookmarksBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    companion object {
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }
}