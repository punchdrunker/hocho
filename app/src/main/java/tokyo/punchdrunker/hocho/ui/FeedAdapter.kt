package tokyo.punchdrunker.hocho.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.GlideApp
import tokyo.punchdrunker.hocho.data.GoogleBlogXml
import tokyo.punchdrunker.hocho.databinding.ItemArticleBinding

class FeedAdapter(val context: Context, private var articles: List<GoogleBlogXml>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: ArticleClickListener? = null

    interface ArticleClickListener {
        fun onClick(view: View, url: String)
    }
    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val holder = ArticleViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
        holder.itemView.setOnClickListener({

            onClickListener?.onClick(it, articles[holder.adapterPosition].articleUrl())
        })
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
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
                    GlideApp.with(context).load(article.imageUrl()).into(entryImage)
                }
            }
        }
    }

    fun setOnclickListener(listener: ArticleClickListener) {
        onClickListener = listener
    }

    inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}