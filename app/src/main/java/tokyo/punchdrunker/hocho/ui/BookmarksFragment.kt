package tokyo.punchdrunker.hocho.ui

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.data.service.BookmarkService
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.ViewUtil.bitmapFromVectorDrawable
import tokyo.punchdrunker.hocho.databinding.FragmentBookmarksBinding

class BookmarksFragment : Fragment() {
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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
            service.fetchAll().subscribe { items ->
                val feedAdapter = FeedAdapter(context, items, false)
                val listener = object : FeedAdapter.ArticleClickListener {
                    override fun onClick(view: View, url: String) {
                        view.context.run {
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
    }

    companion object {
        fun newInstance(): BookmarksFragment = BookmarksFragment()
    }
}