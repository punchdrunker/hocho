package tokyo.punchdrunker.hocho.data

/**
 * Created by nanao on 18/01/13.
 */
interface BlogXml {
    fun getBlogTitle() : String
    fun getBody() : String?
    fun getUrl() : String
    fun getPublishedDate() : String
    fun getImageUrl() : String?
}