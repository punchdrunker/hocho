package tokyo.punchdrunker.hocho.ui

import tokyo.punchdrunker.hocho.data.BlogXml

class BlogPostListItemViewModel(post: BlogXml) {
    val title = post.getBlogTitle()
    val body = post.getBody()
    val imageUrl = post.getImageUrl()
    val url = post.getUrl()
    val publishedDate = post.getPublishedDate()
}