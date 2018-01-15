package tokyo.punchdrunker.hocho.data.response

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Namespace(reference = "http://www.w3.org/2005/Atom")

@Root(name="feed", strict = false)
class GoogleBlogResponse {
    @set:Element
    @get:Element
    var id: String = ""

    @set:ElementList(inline = true)
    @get:ElementList(inline = true)
    var googleBlogList: List<GoogleBlogXml>? = null
}