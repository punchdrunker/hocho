package tokyo.punchdrunker.hocho.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tokyo.punchdrunker.hocho.databinding.FragmentBlogsBinding

class BlogsFragment : Fragment() {
    private lateinit var binding: FragmentBlogsBinding
    private lateinit var blogsViewPagerAdapter: BlogsViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        blogsViewPagerAdapter = BlogsViewPagerAdapter(childFragmentManager)
        binding.blogsViewPager.adapter = blogsViewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.blogsViewPager)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentBlogsBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    companion object {
        fun newInstance(): BlogsFragment = BlogsFragment()
    }
}

class BlogsViewPagerAdapter(
        fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private val tabs = arrayListOf(Tab.All, Tab.Google, Tab.Gradle)

    sealed class Tab(val title: String) {
        object All : Tab("All")
        object Google : Tab("Google")
        object Gradle : Tab("Gradle")
    }

    override fun getPageTitle(position: Int): CharSequence = tabs[position].title

    override fun getItem(position: Int): Fragment {
        val tab = tabs[position]
        return when (tab) {
            Tab.All -> {
                AllBlogsFragment.newInstance()
            }
            is Tab.Google -> {
                GoogleBlogFragment.newInstance()
            }
            is Tab.Gradle -> {
                AllBlogsFragment.newInstance()
            }
        }
    }

    override fun getCount(): Int = tabs.size
}
