package tokyo.punchdrunker.hocho.data

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import okhttp3.OkHttpClient
import okhttp3.Request

class SimpleTaskLoader(context: Context) : AsyncTaskLoader<String>(context) {
    private var cachedResult: String? = null
    private var started = false

    // バックグラウンドスレッドで実行されるので、クラッシュしない
    override fun loadInBackground(): String {
        val url = "https://mixi.co.jp"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val call = client.newCall(request)
        val response = call.execute()
        return response.body?.string() ?: ""
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

    // メインスレッドに結果を返す
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
