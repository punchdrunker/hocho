package tokyo.punchdrunker.hocho.data.response

interface BlogXml {
    fun getBlogTitle() : String
    fun getBody() : String?
    fun getUrl() : String
    fun getPublishedDate() : String
    fun getImageUrl() : String?
}