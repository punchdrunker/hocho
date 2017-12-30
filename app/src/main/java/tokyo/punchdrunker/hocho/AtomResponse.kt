package tokyo.punchdrunker.hocho

import org.simpleframework.xml.*

@Namespace(reference = "http://www.w3.org/2005/Atom")

@Root(name="feed", strict = false)
class AtomResponse {
    @set:Element
    @get:Element
    var id: String = ""

    @set:ElementList(inline = true)
    @get:ElementList(inline = true)
    var entryList: List<EntryXml>? = null
}