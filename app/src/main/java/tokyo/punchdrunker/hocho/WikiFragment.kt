package tokyo.punchdrunker.hocho

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.FragmentWikiBinding

class WikiFragment : Fragment() {
    private lateinit var binding: FragmentWikiBinding
    private val wikiUrl = "https://github.com/punchdrunker/hocho/wiki"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentWikiBinding.inflate(inflater, container!!, false)
        Timber.v("onCreateView")

        binding.webView.setWebViewClient(WebViewClient())
        binding.webView.loadUrl(wikiUrl)

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
        fun newInstance(): WikiFragment = WikiFragment()
    }
}