package tokyo.punchdrunker.hocho.data.service

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import tokyo.punchdrunker.hocho.data.response.GoogleBlogXml

class BookmarkService(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("_bookmark", Context.MODE_PRIVATE)

    fun fetchAll(): List<GoogleBlogXml> {
        return preferences.all
                .filter { it.value is String }
                .map { GSON.fromJson(it.value as String, GoogleBlogXml::class.java) }
    }

    fun putBookmark(entry: GoogleBlogXml) {
        preferences.edit()
                .putString(entry.articleUrl(), GSON.toJson(entry))
                .apply()
    }

    fun removeBookmark(entry: GoogleBlogXml) {
        if (isBookmarked(entry)) {
            preferences.edit()
                    .remove(entry.articleUrl())
                    .apply()
        }
    }

    fun isBookmarked(entry: GoogleBlogXml): Boolean {
        return preferences.contains(entry.articleUrl())
    }

    companion object Factory {
        private val GSON: Gson = GsonBuilder().create()
        fun create(context: Context): BookmarkService = BookmarkService(context)
    }
}