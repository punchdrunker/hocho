package tokyo.punchdrunker.hocho.data.response

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
class GradleBlogXml : BlogXml {
    @set:Element
    @get:Element
    var title: String = ""

    @set:Element
    @get:Element
    var content: String = ""

    @set:Element
    @get:Element
    var updated: String = ""

    @get:Element
    @set:Element
    var link: Link = Link()

    @Root(name = "link")
    class Link {
        @get:Attribute
        @set:Attribute
        var href: String = ""
    }

    override fun getBlogTitle(): String {
        return title
    }

    override fun getBody(): String? {
        val string = content.replace("\n".toRegex(), " ")
        val contentRegex = """<p>(.*)?</p>""".toRegex()
        val result = contentRegex.find(string)
        if (result != null) {
            val body = result.groupValues[1]
            return body.replace("""<.+?>""".toRegex(), "").subSequence(0, 140).toString() + "..."
        }
        return null
    }

    override fun getUrl(): String {
        return link.href
    }

    override fun getPublishedDate(): String {
        val dateTime = DateTime.parse(updated)
        return DATE_FORMATTER.print(dateTime)
    }

    override fun getImageUrl(): String? {
        val rawBody = content.replace("\n".toRegex(), "")
        val imageContentRegex = """<img src="([^"]*)""".toRegex()
        val result = imageContentRegex.find(rawBody)
        if (result != null) {
            return "https://blog.gradle.org" + result.groupValues[1]
        }
        return null
    }

    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormat.shortDateTime()
    }
}