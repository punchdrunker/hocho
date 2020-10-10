package tokyo.punchdrunker.hocho.transition

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PhotoPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private lateinit var currentFragment: Fragment
    override fun getItem(position: Int): Fragment {
        return PagerItemFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return PhotoStore.getCount()
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        currentFragment = `object` as Fragment
        super.setPrimaryItem(container, position, `object`)
    }

    fun getCurrentFragment():Fragment = currentFragment
}