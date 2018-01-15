package tokyo.punchdrunker.hocho

import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import tokyo.punchdrunker.hocho.data.GradleBlogResponse
import tokyo.punchdrunker.hocho.data.GradleBlogService

class GradleBlogServiceTest {
    private var server = MockWebServer()

    @After
    @Throws(Exception::class)
    fun tearDown() = server.shutdown()

    @Test
    fun fetch() {
        val stream = javaClass.classLoader.getResourceAsStream("gradle-blog.xml")
        val mockXmlString = stream.bufferedReader().use { it.readText()}
        val response = createMockResponse(mockXmlString)
        server.enqueue(response)
        server.start()


        val observer = TestObserver<GradleBlogResponse>()
        val service = createService()
        service.fetch().subscribe(observer)
        observer.assertComplete()
        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.values()[0].entryList!!.run {
            assertEquals(20, size)
            assertEquals("Introducing the new C++ plugins", this[0].title)
            assertEquals("1/10/18 12:00 AM", this[0].getPublishedDate())
            assertEquals("https://blog.gradle.org/introducing-the-new-cpp-plugins", this[0].getUrl())
            assertEquals("This post introduces some new plugins for C++ that we’ve been working on. These plugins can build C++ libraries and applications. They work ...", this[0].getBody())
            assertEquals("https://blog.gradle.org/images/introducing-the-new-cpp-plugins/01-application.gif", this[0].getImageUrl())
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
            .create(GradleBlogService::class.java)
}