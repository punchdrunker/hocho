package tokyo.punchdrunker.hocho.data.service

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.Single
import tokyo.punchdrunker.hocho.data.response.GoogleBlogXml

class BookmarkService(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("_bookmark", Context.MODE_PRIVATE)

    fun fetchAll(): Single<List<GoogleBlogXml>> = Single.create {
        it.onSuccess(preferences.all
                .filter { it.value is String }
                .map { GSON.fromJson(it.value as String, GoogleBlogXml::class.java) }
        )
    }

    fun putBookmark(entry: GoogleBlogXml): Completable {
        return Single.just(entry).map {
            preferences.edit()
                    .putString(it.articleUrl(), GSON.toJson(it))
                    .apply()
        }.toCompletable()
    }

    fun removeBookmark(entry: GoogleBlogXml): Completable = isBookmarked(entry).map { it ->
        when (it) {
            true -> {
                preferences.edit()
                        .remove(entry.articleUrl())
                        .apply()
            }
            else -> Unit
        }
    }.toCompletable()

    fun isBookmarked(entry: GoogleBlogXml): Single<Boolean> = Single.just(entry).map {
        preferences.contains(entry.articleUrl())
    }

    companion object Factory {
        private val GSON: Gson = GsonBuilder().create()
        fun create(context: Context): BookmarkService = BookmarkService(context)
    }
}