package tokyo.punchdrunker.hocho.data

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
class GoogleBlogXml: BlogXml {
    @set:Element
    @get:Element
    var title: String = ""

    @set:Element
    @get:Element
    var content: String = ""

    @set:Element
    @get:Element
    private var published: String = ""

    @set:Element
    @get:Element
    private var origLink: String = ""

    fun dateForDisplay(): String {
        try {
            val dateTime = DateTime.parse(published)
            return DATE_FORMATTER.print(dateTime)
        } catch (e: IllegalArgumentException) {
            return "jfjfj"
        }
    }

    fun shortContent(): String? {
        val string = content.replace("\n".toRegex(), " ")
        val contentRegex = """<p>(.*)?</p>""".toRegex()
        val result = contentRegex.find(string)
        if (result != null) {
            val body = result.groupValues[1]
            return body.replace("""<.+?>""".toRegex(), "").subSequence(0, 140).toString() + "..."
        }
        return null
    }

    fun imageUrl(): String? {
        val rawBody = content.replace("\n".toRegex(), "")
        val imageContentRegex = """<meta name="twitter:image" content="([^"]*)""".toRegex()
        val result = imageContentRegex.find(rawBody)
        if (result != null) {
            return result.groupValues[1]
        }
        return null
    }

    fun articleUrl(): String {
        return origLink
    }

    override fun getBlogTitle(): String {
        return title
    }

    override fun getBody(): String? {
        return this.shortContent()
    }

    override fun getUrl(): String {
        return origLink
    }

    override fun getPublishedDate(): String {
        return this.dateForDisplay()
    }

    override fun getImageUrl(): String? {
        return imageUrl()
    }

    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormat.shortDateTime()
    }
}