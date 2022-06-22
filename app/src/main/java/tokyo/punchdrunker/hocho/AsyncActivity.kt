package tokyo.punchdrunker.hocho

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import tokyo.punchdrunker.hocho.data.AtomResponse
import tokyo.punchdrunker.hocho.data.BlogService
import tokyo.punchdrunker.hocho.data.BlogServiceWithCoroutine
import tokyo.punchdrunker.hocho.data.SimpleTaskLoader
import tokyo.punchdrunker.hocho.databinding.ActivityAsyncBinding

class AsyncActivity : AppCompatActivity() {

    private val loaderId = 1
    private val extraParam = "extra_param"

    private val binding: ActivityAsyncBinding by lazy {
        DataBindingUtil.setContentView<ActivityAsyncBinding>(this, R.layout.activity_async)
    }

    private val callback = object : LoaderManager.LoaderCallbacks<String> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
            return SimpleTaskLoader(this@AsyncActivity)
        }

        // deliverResultされたものが、bodyに入ってくる
        override fun onLoadFinished(loader: Loader<String>, body: String) {
            LoaderManager.getInstance(this@AsyncActivity).destroyLoader(loader.id)
            Snackbar.make(binding.root, "resopnse body: $body", Snackbar.LENGTH_SHORT).show()
        }

        override fun onLoaderReset(loader: Loader<String>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        setupToolbar()
    }

    // OkHttpを使った通信
    fun crashApp(view: View) {
        val url = "https://mixi.co.jp"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        // 準備せずに同期実行するとクラッシュする(execute)
        val response = call.execute()

        Snackbar.make(view, "Blog title: " + response.body?.string(), Snackbar.LENGTH_SHORT).show()
    }

    // AsyncTaskLoader を使った通信
    fun requestOnBackground() {
        val args = Bundle()
        args.putString(extraParam, "サンプルパラメータ")
        LoaderManager.getInstance(this).initLoader(loaderId, args, callback)
    }

    // Retrofit を使った通信
    fun requestWithRetrofit(view: View) {
        val service = BlogService.create()
        service.fetch("rss").enqueue(object : Callback<AtomResponse> {
            override fun onResponse(call: Call<AtomResponse>, response: Response<AtomResponse>) {
                val atom = response.body()
                Snackbar.make(view, "Blog title: " + atom?.entryList!![0].title, Snackbar.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<AtomResponse>, t: Throwable) {
                Timber.w(t)
            }
        })
    }

    // Retrofit + Coroutine を使った通信 (kotlin-coroutines-retrofit moduleを使用)
    fun requestWithRetrofitAndCoroutine(view: View) {
        val service = BlogServiceWithCoroutine.create()
        lifecycleScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            val response = service.fetch("rss").await()
            Snackbar.make(view, "[COROUTINE]Blog title: " + response.entryList!![0].title, Snackbar.LENGTH_SHORT).show()
        }
        Snackbar.make(view, "TEST TONE 1", Snackbar.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = getString(R.string.async)
    }
}
