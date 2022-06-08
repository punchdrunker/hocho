package tokyo.punchdrunker.hocho

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import tokyo.punchdrunker.hocho.data.BlogService

class BlogServiceTest {
    private var server = MockWebServer()

    @After
    @Throws(Exception::class)
    fun tearDown() = server.shutdown()

    @Test
    fun fetchFeed() {
        val stream = javaClass.classLoader!!.getResourceAsStream("android-developer-blog.xml")
        val mockXmlString = stream.bufferedReader().use { it.readText()}
        val response = createMockResponse(mockXmlString)
        server.enqueue(response)
        server.start()

        val service = createService()
        val actual = service.fetch("rss")
                .execute()
        actual.body()!!.entryList!!.run {
            assertEquals(25, size)
            assertEquals("Creating an app to help your community during the pandemic with Gaston Saillen #IamaGDE", this[0].title)
            assertEquals("2021/12/20 9:59", this[0].dateForDisplay())
            assertEquals("http://android-developers.googleblog.com/2021/12/creating-app-to-help-your-community.html", this[0].articleUrl())
            assertEquals("Posted by Alicja Heisig, Developer Relations Program Manager   Welcome to #IamaGDE - a series of spotlights presenting Google Developer Expe...", this[0].shortContent())
            assertEquals("https://blogger.googleusercontent.com/img/a/AVvXsEgHl1OMxu_r7B6C2OHY0OsAyNn8j00v_A2ASjMKfjrw2JO0B9fbD6-5uohhucBeSLh_6ngRqhyARan_Tn1Sd-veSxOc3b-Lo1H_Q0PktyH5hrpCkhX54T8PoLWqFlfkRWIIy_0GWkK84s1CgfSk8w05GanB28I5mnb4vDPrHqG0CBmkcNuiqNygPjgP", this[0].imageUrl())
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