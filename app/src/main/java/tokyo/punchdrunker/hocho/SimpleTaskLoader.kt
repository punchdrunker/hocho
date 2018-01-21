package tokyo.punchdrunker.hocho

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import okhttp3.OkHttpClient
import okhttp3.Request

class SimpleTaskLoader(context: Context) : AsyncTaskLoader<String>(context) {
    private var cachedResult: String? = null
    private var started = false

    override fun loadInBackground(): String {
        var body = ""

        val url = "https://mixi.co.jp"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        val response = call.execute()
        body = response.body().toString()

        return "task complete: " + body
    }

    override fun onStartLoading() {
        if (cachedResult != null) {
            deliverResult(cachedResult)
            return
        }
        if (cachedResult == null || takeContentChanged()) {
            forceLoad()
        }
    }

    override fun deliverResult(data: String?) {
        if (isReset) {
            if (cachedResult != null) {
                cachedResult = null
            }
            return
        }
        cachedResult = data
        if (isStarted) {
            super.deliverResult(data)
        }
    }

    override fun onForceLoad() {
        super.onForceLoad()
        started = true
    }

    override fun onStopLoading() {
        cancelLoad()
        super.onStopLoading()
    }

    override fun onReset() {
        onStopLoading()
        super.onReset()
    }
}
