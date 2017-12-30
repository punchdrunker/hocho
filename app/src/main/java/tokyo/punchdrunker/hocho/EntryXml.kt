package tokyo.punchdrunker.hocho

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

    fun shortContent(): String {
        var string = content.replace("\n".toRegex(), "")
        return string.replace("""<.+?>""".toRegex(), "").subSequence(0, 140).toString() + "..."
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
}