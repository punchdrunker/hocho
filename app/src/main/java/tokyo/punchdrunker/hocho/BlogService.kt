package tokyo.punchdrunker.hocho

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BlogService {
    @GET("/feeds/posts/default")
    fun fetch(@Query("alt") alt: String): Call<AtomResponse>

    companion object Factory {
        fun create(): BlogService {
            val logging = HttpLoggingInterceptor()
            val logLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            logging.setLevel(logLevel)

            val okhttp = OkHttpClient.Builder()
                    .addInterceptor(logging) // response bodyをlogcatに流す
                    .addNetworkInterceptor(StethoInterceptor()) // Stethoでの監視を有効にする
                    .build()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(okhttp)
                    .baseUrl("https://android-developers.googleblog.com/")
                    .build()

            return retrofit.create<BlogService>(BlogService::class.java)
        }
    }
}