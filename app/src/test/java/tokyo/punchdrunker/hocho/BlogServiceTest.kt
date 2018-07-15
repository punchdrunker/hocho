package tokyo.punchdrunker.hocho

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class BlogServiceTest {
    private var server = MockWebServer()

    @After
    @Throws(Exception::class)
    fun tearDown() = server.shutdown()

    @Test
    fun fetchFeed() {
        val stream = javaClass.classLoader.getResourceAsStream("android-developer-blog.xml")
        val mockXmlString = stream.bufferedReader().use { it.readText()}
        val response = createMockResponse(mockXmlString)
        server.enqueue(response)
        server.start()

        val service = createService()
        val actual = service.fetch("rss")
                .execute()
        actual.body()!!.entryList!!.run {
            assertEquals(24, size)
            assertEquals("Phasing out legacy recommendations on Android TV", this[0].title)
            assertEquals("12/22/17 10:00 AM", this[0].dateForDisplay())
            assertEquals("http://android-developers.googleblog.com/2017/12/phasing-out-legacy-recommendations-on.html", this[0].articleUrl())
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
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(BlogService::class.java)
}
