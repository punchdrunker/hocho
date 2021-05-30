package tokyo.punchdrunker.hocho

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import tokyo.punchdrunker.hocho.databinding.FragmentBookmarksBinding

import androidx.browser.customtabs.CustomTabColorSchemeParams

class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarksBinding.inflate(inflater, container!!, false)
        recyclerView = binding.myRecyclerView
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        fetchBookmarks()
    }

    private fun fetchBookmarks() {
        val context = this.context ?: return
        val service = BookmarkService.create(context)
        recyclerView.run {
            val items = service.fetchAll()
            val feedAdapter = FeedAdapter(context, items, false)
            val listener = object : FeedAdapter.ArticleClickListener {
                override fun onClick(view: View, url: String) {
                    view.context.run {
                        val backArrow = bitmapFromVectorDrawable(this, R.drawable.ic_arrow_back)
                        val params = CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(
                                ContextCompat.getColor(
                                    activity!!,
                                    R.color.colorPrimary
                                )
                            )
                            .build()
                        val tabsIntent = CustomTabsIntent.Builder()
                            .setShowTitle(true)
                            .setCloseButtonIcon(backArrow)
                            .setDefaultColorSchemeParams(params)
                            .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                            .setExitAnimations(
                                this,
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right
                            )
                            .build()

                        tabsIntent.launchUrl(this, Uri.parse(url))
                    }
                }
            }
            feedAdapter.setOnclickListener(listener)
            adapter = feedAdapter
        }
    }

    companion object {
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }
}