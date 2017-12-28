package tokyo.punchdrunker.hocho

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.io.File

// @RunWith(AndroidJUnit4::class)
class BlogServiceTest {
    private var server = MockWebServer()

    @After
    @Throws(Exception::class)
    fun tearDown() = server.shutdown()

    @Test
    fun fetchFeed() {
        val stream = File("app/src/test/assets", "android-developer-blog.xml").inputStream()
        val mockXmlString = stream.bufferedReader().use { it.readText()}
        val response = createMockResponse().setBody(mockXmlString)
        server.enqueue(response)

//        val url = server.url("/")
        val service = createService()
        val actual = service.fetch("rss")
                .execute()
        actual.body()!!.entryList!!.run {
            assertEquals(25, size)
            assertEquals("Phasing out legacy recommendations on Android TV", this[0].title)
        }

    }

    private fun createMockResponse() = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(200)

    private fun createService() = Retrofit.Builder()
            .baseUrl("https://android-developers.googleblog.com/")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(BlogService::class.java)
}