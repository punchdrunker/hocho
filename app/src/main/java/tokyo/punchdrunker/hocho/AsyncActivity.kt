package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.view.View
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ActivityAsyncBinding


class AsyncActivity : AppCompatActivity() {

    private var taskResult = ""
    private val loaderId = 1
    private val extraParam = "extra_param"

    private val binding: ActivityAsyncBinding by lazy {
        DataBindingUtil.setContentView<ActivityAsyncBinding>(this, R.layout.activity_async)
    }

    private val callback = object : LoaderManager.LoaderCallbacks<String> {
        override fun onCreateLoader(id: Int, args: Bundle): Loader<String> {
            return SimpleTaskLoader(this@AsyncActivity)
        }

        override fun onLoadFinished(loader: Loader<String>, data: String) {
            supportLoaderManager.destroyLoader(loader.id)
            taskResult = data
            Timber.d(taskResult)
        }

        override fun onLoaderReset(loader: Loader<String>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        setupToolbar()
    }

    fun crashApp(view: View) {
        httpRequest()
    }

    fun requestOnBackground(view: View) {
        val args = Bundle()
        args.putString(extraParam, "サンプルパラメータ")
        supportLoaderManager.initLoader<String>(loaderId, args, callback)
    }

    private fun httpRequest() {
        val url = "https://mixi.co.jp"
        val client = OkHttpClient()

        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        call.execute()
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