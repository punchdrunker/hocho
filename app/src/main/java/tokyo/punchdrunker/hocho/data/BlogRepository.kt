package tokyo.punchdrunker.hocho.data

import io.reactivex.Observable
import io.reactivex.Single

class BlogRepository {
    fun loadAllBlogs(): Observable<List<BlogXml>> {
        return Observable.merge(loadGoogleBlog().toObservable(), loadGradleBlog().toObservable())
    }

    fun loadGoogleBlog(): Single<List<BlogXml>> {
        val service = GoogleBlogService.create()
        return service.fetch("rss").map { resp ->
            resp.googleBlogList
        }
    }

    fun loadGradleBlog(): Single<List<BlogXml>> {
        val service = GradleBlogService.create()
        return service.fetch().map { resp ->
            resp.entryList
        }
    }
}