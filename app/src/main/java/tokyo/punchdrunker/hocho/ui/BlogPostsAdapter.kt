package tokyo.punchdrunker.hocho.ui

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber
import tokyo.punchdrunker.hocho.GlideApp
import tokyo.punchdrunker.hocho.data.BlogXml
import tokyo.punchdrunker.hocho.databinding.ItemBlogPostBinding



class BlogPostsAdapter(
        val context: Context,
        private var posts: ObservableArrayList<BlogXml>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        posts.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<BlogXml>>() {
            override fun onChanged(contents: ObservableList<BlogXml>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(contents: ObservableList<BlogXml>, i: Int, i1: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeInserted(contents: ObservableList<BlogXml>, i: Int, i1: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeMoved(contents: ObservableList<BlogXml>, i: Int, i1: Int, i2: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(contents: ObservableList<BlogXml>, i: Int, i1: Int) {
                notifyDataSetChanged()
            }
        })
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is BlogPostViewHolder) {
            holder.binding.viewModel = BlogPostListItemViewModel(posts[position])
            val post = posts[position]
            holder.binding.apply {
                if (post.getImageUrl().isNullOrEmpty()) {
                    entryImage.visibility = View.GONE
                } else {
                    entryImage.visibility = View.VISIBLE
                    GlideApp.with(context).load(post.getImageUrl()).into(entryImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val holder = BlogPostViewHolder(ItemBlogPostBinding.inflate(inflater, parent, false))
        holder.binding.entryImage
        return holder
    }

    override fun getItemCount(): Int {
        Timber.d(posts.size.toString())
        return posts.size
    }


    inner class BlogPostViewHolder(val binding: ItemBlogPostBinding) : RecyclerView.ViewHolder(binding.root)
}