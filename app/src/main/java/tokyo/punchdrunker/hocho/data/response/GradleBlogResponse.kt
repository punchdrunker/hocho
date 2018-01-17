package tokyo.punchdrunker.hocho.data.response

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Namespace(reference = "http://www.w3.org/2005/Atom")

@Root(name="feed", strict = false)
class GradleBlogResponse {
    @set:ElementList(inline = true)
    @get:ElementList(inline = true)
    var entryList: List<GradleBlogXml>? = null
}