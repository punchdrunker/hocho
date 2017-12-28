package tokyo.punchdrunker.hocho

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ItemArticleBinding

class FeedAdapter(val context: Context, var articles: List<EntryXml>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        Timber.d(articles.size.toString())
        return articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ArticleViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ArticleViewHolder) {
            holder.binding.apply {
                title.text = articles[position].title
                description.text = articles[position].shortContent()
            }
        }
    }

    inner class ArticleViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}