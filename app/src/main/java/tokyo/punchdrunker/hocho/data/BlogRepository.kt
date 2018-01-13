package tokyo.punchdrunker.hocho.data

import io.reactivex.Single

class BlogRepository {
    fun loadAllBlogs(): Single<List<BlogXml>> {
        val service = GoogleBlogService.create()
        return service.fetch("rss").map { resp ->
            resp.googleBlogList
        }
    }
    fun loadGoogleBlog() : Single<List<BlogXml>> {
        val service = GoogleBlogService.create()
        return service.fetch("rss").map { resp ->
            resp.googleBlogList
        }
    }
    fun loadGradleBlog() {}
}