package tokyo.punchdrunker.hocho.ui

import android.databinding.ObservableArrayList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import tokyo.punchdrunker.hocho.data.BlogRepository
import tokyo.punchdrunker.hocho.data.BlogXml

class BlogPostListViewModel constructor(private val repository: BlogRepository) {
    var posts: ObservableArrayList<BlogXml> = ObservableArrayList()

    fun loadGradleBlog() {
        repository.loadGradleBlog()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    posts.clear()
                    posts.addAll(list)
                }, { e ->
                    Timber.e(e)
                })
    }

    fun loadAllBlogs() {
        posts.clear()
        repository.loadAllBlogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    posts.addAll(list)
                }, { e ->
                    Timber.e(e)
                })
    }
}