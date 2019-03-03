package tokyo.punchdrunker.hocho.transition

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.FragmentFromBinding


class FromFragment : Fragment(), TransitionNavigator {
    private lateinit var binding: FragmentFromBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_from, container, false)
        binding.list.adapter = PhotoAdapter(this)

        return binding.root
    }

    override fun transition(v: View, position: Int) {
        when (position % 4) {
            0 -> openToActivity(v, position)
            1 -> openToActivityWithScroll(v, position)
            2 -> openToActivityWithScrollWaiting(v, position)
            3 -> openPagerActivityWithScrollWaiting(v, position)
        }
    }

    private fun openToActivity(view: View, position: Int) {
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.shared_element)).toBundle()
        startActivity(intent, option)
    }

    private fun openToActivityWithScroll(view: View, position: Int) {
        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
        }
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.shared_element)).toBundle()
        startActivity(intent, option)
    }

    private fun openToActivityWithScrollWaiting(view: View, position: Int) {
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.shared_element)).toBundle()

        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
            Handler().postDelayed({
                startActivity(intent, option)
            }, 200)
        } else {
            startActivity(intent, option)
        }
    }

    private fun openPagerActivityWithScrollWaiting(view: View, position: Int) {
        val intent = PagerActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.shared_element)).toBundle()

        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
            Handler().postDelayed({
                startActivity(intent, option)
            }, 200)
        } else {
            startActivity(intent, option)
        }
    }

    // app barの下や画面下部にImageViewが隠れている？
    private fun shoulScrollList(cell: View): Boolean {
        val cellLocation = IntArray(2)
        cell.getLocationOnScreen(cellLocation)
        val cellTop = cellLocation[1]
        val listLocation = IntArray(2)
        binding.list.getLocationOnScreen(listLocation)
        val diffToAppBar = cellTop - listLocation[1]

        val cellBottom = cellTop + binding.list.height
        val listBottom = listLocation[1] + binding.list.height
        val diffToBottom = cellBottom - listBottom

        return (diffToAppBar < 0 || diffToBottom > 0)
    }

    private fun prepareEnterTransition() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                val viewHolder = binding.list.findViewHolderForAdapterPosition(2)
                val itemView = viewHolder?.itemView ?: return
                val photoView = itemView.findViewById<ImageView>(R.id.card_photo)
                sharedElements!![names!![0]] = photoView
            }
        })
    }

    companion object {
        fun newInstance() = FromFragment()
    }
}
