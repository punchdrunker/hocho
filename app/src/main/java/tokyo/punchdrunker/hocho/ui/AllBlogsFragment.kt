package tokyo.punchdrunker.hocho.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.data.BlogRepository
import tokyo.punchdrunker.hocho.databinding.FragmentArticlesBinding

class AllBlogsFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel= BlogPostListViewModel(BlogRepository())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container!!, false)
        recyclerView = binding.myRecyclerView
        recyclerView.adapter = BlogPostsAdapter(context!!, viewModel.posts)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadAllBlogs()
    }

    companion object {
        fun newInstance(): AllBlogsFragment = AllBlogsFragment()
    }
}