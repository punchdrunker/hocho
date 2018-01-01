package tokyo.punchdrunker.hocho

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.simpleframework.xml.Element
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

    @set:Element
    @get:Element
    var origLink: String = ""

    fun dateForDisplay(): String {
        val dateTime = DateTime.parse(published)
        val format = DateTimeFormat.shortDateTime()
        return format.print(dateTime)
    }

    fun shortContent(): String? {
        var string = content.replace("\n".toRegex(), " ")
        val contentRegex = """<p>(.*)?</p>""".toRegex()
        val result = contentRegex.find(string)
        if (result != null) {
            var body = result.groupValues[1]
            return body.replace("""<.+?>""".toRegex(), "").subSequence(0, 140).toString() + "..."
        } else {
            return null
        }
    }

    fun imageUrl(): String? {
        var rawBody = content.replace("\n".toRegex(), "")
        val imageContentRegex = """<meta name="twitter:image" content="([^"]*)""".toRegex()
        val result = imageContentRegex.find(rawBody)
        if (result != null) {
            return result.groupValues[1]
        } else {
            return null
        }

    }

    fun articleUrl(): String {
        return origLink
    }
}