package tokyo.punchdrunker.hocho.data

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBlogService {
    @GET("/feeds/posts/default")
    fun fetch(@Query("alt") alt: String): Single<GoogleBlogResponse>

    companion object Factory {
        fun create(): GoogleBlogService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(OkHttpClient())
                    .baseUrl("https://android-developers.googleblog.com/")
                    .build()

            return retrofit.create<GoogleBlogService>(GoogleBlogService::class.java)
        }
    }
}