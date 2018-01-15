package tokyo.punchdrunker.hocho.ui

import android.view.View
import tokyo.punchdrunker.hocho.data.BlogXml
import tokyo.punchdrunker.hocho.usecase.OpenBlogItemUseCase

class BlogPostListItemViewModel(post: BlogXml, val openUseCase: OpenBlogItemUseCase) {
    val title = post.getBlogTitle()
    val body = post.getBody()
    val imageUrl = post.getImageUrl()
    val url = post.getUrl()
    val publishedDate = post.getPublishedDate()

    fun openBlog(view: View) {
        openUseCase.run(url)
    }
}