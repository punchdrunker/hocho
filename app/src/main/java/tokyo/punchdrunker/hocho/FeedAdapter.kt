package tokyo.punchdrunker.hocho

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ItemArticleBinding

class FeedAdapter(val context: Context, private var articles: List<EntryXml>, private val fixSize: Boolean = true) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: ArticleClickListener? = null
    private val bookmarkService = BookmarkService(context)
    private val bookmarkedImageId = R.drawable.ic_bookmark_black_24dp
    private val unbookmarkedImageId = R.drawable.ic_bookmark_border_black_24dp

    interface ArticleClickListener {
        fun onClick(view: View, url: String)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val holder = ArticleViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
        holder.itemView.setOnClickListener({
            onClickListener?.onClick(it, articles[holder.adapterPosition].articleUrl())
        })
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val article = articles[position]
            holder.binding.apply {
                title.text = article.title
                description.text = article.shortContent()
                publishedDate.text = article.dateForDisplay()
                if (article.imageUrl().isNullOrEmpty()) {
                    entryImage.visibility = View.GONE
                } else {
                    entryImage.visibility = View.VISIBLE
                    Glide.with(context).load(article.imageUrl()).into(entryImage)
                }
                val buttonImageId = if (bookmarkService.isBookmarked(article)) {
                    bookmarkedImageId
                } else {
                    unbookmarkedImageId
                }
                bookmarkButton.setImageResource(buttonImageId)
                bookmarkButton.setOnClickListener { view ->
                    bookmarkService.run {
                        when (isBookmarked(article)) {
                            true -> {
                                if (!fixSize) {
                                    articles = articles.filter { it != article }
                                    notifyDataSetChanged()
                                }
                                removeBookmark(article)
                                bookmarkButton.setImageResource(unbookmarkedImageId)
                            }
                            else -> {
                                putBookmark(article)
                                bookmarkButton.setImageResource(bookmarkedImageId)
                            }
                        }
                    }
                }
            }
        }
    }

    fun setOnclickListener(listener: ArticleClickListener) {
        onClickListener = listener
    }

    inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}
