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
        var str = content
        str = str.replace("""<.+?>""".toRegex(), "")
        return str
    }
}