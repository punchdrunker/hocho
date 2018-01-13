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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.ViewUtil.bitmapFromVectorDrawable
import tokyo.punchdrunker.hocho.data.GoogleBlogService
import tokyo.punchdrunker.hocho.databinding.FragmentGoogleBlogBinding

class GoogleBlogFragment: Fragment() {
    private lateinit var binding: FragmentGoogleBlogBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoogleBlogBinding.inflate(inflater, container!!, false)
        recyclerView = binding.myRecyclerView
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchEntries()
    }

    private fun fetchEntries() {
        val service = GoogleBlogService.create()
        service.fetch("rss")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { atom ->
                    recyclerView.run {
                        setHasFixedSize(true)

                        val feedAdapter = FeedAdapter(context, atom.googleBlogList!!)
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
    }

    companion object {
        fun newInstance(): GoogleBlogFragment = GoogleBlogFragment()
    }
}