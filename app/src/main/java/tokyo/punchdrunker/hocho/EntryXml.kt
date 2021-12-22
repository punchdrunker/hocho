package tokyo.punchdrunker.hocho

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
class EntryXml {
    @set:Element
    @get:Element
    var title: String = ""

    @set:Element
    @get:Element
    var content: String = ""

    @set:Element
    @get:Element
    var published: String = ""

    @set:ElementList(inline = true)
    @get:ElementList(inline = true)
    var links: List<Link>? = null

    fun dateForDisplay(): String {
        val dateTime = DateTime.parse(published)
        return DATE_FORMATTER.print(dateTime)
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

    fun articleUrl(): String? {
        return links?.first {
            it.rel == "alternate"
        }?.href
    }

    companion object {
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormat.shortDateTime()
    }
}

@Root(name = "link", strict = false)
class Link {
    @get:Attribute
    @set:Attribute
    var rel: String = ""

    @get:Attribute
    @set:Attribute
    var href: String = ""
}

