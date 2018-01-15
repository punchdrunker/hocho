package tokyo.punchdrunker.hocho.data

import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import tokyo.punchdrunker.hocho.data.response.GoogleBlogResponse
import tokyo.punchdrunker.hocho.data.service.GoogleBlogService

class GoogleBlogServiceTest {
    private var server = MockWebServer()

    @After
    @Throws(Exception::class)
    fun tearDown() = server.shutdown()

    @Test
    fun fetch() {
        val stream = javaClass.classLoader.getResourceAsStream("android-developer-blog.xml")
        val mockXmlString = stream.bufferedReader().use { it.readText()}
        val response = createMockResponse(mockXmlString)
        server.enqueue(response)
        server.start()


        val observer = TestObserver<GoogleBlogResponse>()
        val service = createService()
        service.fetch("rss").subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.values()[0].googleBlogList!!.run {
            assertEquals(25, size)
            assertEquals("Phasing out legacy recommendations on Android TV", this[0].title)
            assertEquals("12/22/17 10:00 AM", this[0].dateForDisplay())
            assertEquals("http://android-developers.googleblog.com/2017/12/phasing-out-legacy-recommendations-on.html", this[0].getUrl())
            assertEquals(" At Google I/O 2017, we announced a redesign of the Android TV's home screen. We expanded the recommendation row concept so that each app ca...", this[0].shortContent())
            assertEquals("https://1.bp.blogspot.com/-RikbUBpFVUo/Wjwe6_5qNdI/AAAAAAAAE8A/-oxT_LLrYzsToH0Jf2XOjeOAV3DIkUf4ACLcBGAs/s1600/image1.png", this[0].imageUrl())
        }
    }

    private fun createMockResponse(mockBody: String) = MockResponse()
            .addHeader("Content-Type", "application/xml")
            .setResponseCode(200)
            .setBody(mockBody)

    private fun createService() = Retrofit.Builder()
            .baseUrl(server.url("/").toString())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(GoogleBlogService::class.java)
}