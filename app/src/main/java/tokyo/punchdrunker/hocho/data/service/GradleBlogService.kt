package tokyo.punchdrunker.hocho.data.service

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import tokyo.punchdrunker.hocho.data.response.GradleBlogResponse

interface GradleBlogService {
    @GET("/blog.atom")
    fun fetch(): Single<GradleBlogResponse>

    companion object Factory {
        fun create(): GradleBlogService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .client(OkHttpClient())
                    .baseUrl("https://feed.gradle.org/")
                    .build()

            return retrofit.create<GradleBlogService>(GradleBlogService::class.java)
        }
    }
}