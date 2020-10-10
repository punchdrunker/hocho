package tokyo.punchdrunker.hocho

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container!!, false)
        recyclerView = binding.myRecyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchEntries()
    }

    private fun fetchEntries() {
        val service = BlogService.create()
        service.fetch("rss").enqueue(object : Callback<AtomResponse> {
            override fun onResponse(call: Call<AtomResponse>, response: Response<AtomResponse>) {
                val atom = response.body()
                if (atom?.entryList == null) return

                recyclerView.run {
                    setHasFixedSize(true)

                    val feedAdapter = FeedAdapter(context, atom.entryList!!)
                    val listener = object : FeedAdapter.ArticleClickListener {
                        override fun onClick(view: View, url: String) {
                            context.run {
                                val backArrow = bitmapFromVectorDrawable(this, R.drawable.ic_arrow_back)
                                val tabsIntent = CustomTabsIntent.Builder()
                                        .setShowTitle(true)
                                        .setCloseButtonIcon(backArrow)
                                        .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                                        .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                                        .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                        .build()

                                tabsIntent.launchUrl(this, Uri.parse(url))
                            }
                        }
                    }
                    feedAdapter.setOnclickListener(listener)
                    adapter = feedAdapter
                }
            }

            override fun onFailure(call: Call<AtomResponse>, t: Throwable) {
                Timber.w(t)
            }
        })
    }

    companion object {
        fun newInstance(): ArticlesFragment = ArticlesFragment()
    }
}