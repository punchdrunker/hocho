package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import retrofit2.Callback
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        recyclerView = binding.myRecyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = this@MainActivity.layoutManager
            val items = arrayListOf(
                    Article("title1", "ggg"),
                    Article("title2", "hogehge"),
                    Article("asdfa", "asfassd"))
            adapter = FeedAdapter(this@MainActivity, items)
        }
        fetchEntries()
    }

    fun fetchEntries() {
        val service = BlogService.create()
        service.fetch("rss").enqueue(object : Callback<AtomResponse> {
            override fun onResponse(call: Call<AtomResponse>, response: Response<AtomResponse>) {
                val atom = response.body()
                if (atom != null) {
                    atom.entryList
                }
            }

            override fun onFailure(call: Call<AtomResponse>, t: Throwable) {
                Timber.d(t)
            }
        })
    }
}
