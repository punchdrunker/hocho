package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import tokyo.punchdrunker.hocho.ViewUtil.bitmapFromVectorDrawable
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        recyclerView = binding.myRecyclerView
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
                    layoutManager = this@MainActivity.layoutManager

                    val feedAdapter = FeedAdapter(this@MainActivity, atom.entryList!!)
                    val listener = object : FeedAdapter.ArticleClickListener {
                        override fun onClick(view: View, url: String) {
                            this@MainActivity.run {
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
                Timber.d(t)
            }
        })
    }
}
