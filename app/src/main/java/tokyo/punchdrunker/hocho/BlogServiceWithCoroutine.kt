package tokyo.punchdrunker.hocho

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogServiceWithCoroutine {
    @GET("/feeds/posts/default")
    fun fetch(@Query("alt") alt: String): Deferred<AtomResponse>

    companion object Factory {
        fun create(): BlogServiceWithCoroutine {
            val logging = HttpLoggingInterceptor()
            val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            logging.level = logLevel

            val okhttp = OkHttpClient.Builder()
                    .addInterceptor(logging) // response bodyをlogcatに流す
                    .addNetworkInterceptor(StethoInterceptor()) // Stethoでの監視を有効にする
                    .build()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(okhttp)
                    .baseUrl("https://android-developers.googleblog.com/")
                    .build()

            return retrofit.create<BlogServiceWithCoroutine>(BlogServiceWithCoroutine::class.java)
        }
    }
}