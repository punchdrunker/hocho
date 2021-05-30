package tokyo.punchdrunker.hocho.transition

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.FragmentFromBinding

class FromFragment : Fragment(), TransitionNavigator {
    private lateinit var binding: FragmentFromBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_from, container, false)
        binding.list.adapter = PhotoAdapter(this)

        return binding.root
    }

    fun getViewHolder(position: Int): RecyclerView.ViewHolder? {
        return binding.list.findViewHolderForAdapterPosition(position)
    }

    override fun onStart() {
        super.onStart()
        scrollToCurrentPosition()
    }

    override fun transition(v: View, position: Int) {
        PhotoStore.setCurrentPosition(activity as Context, position)
        when (position % 5) {
            0 -> openToActivity(v, position)
            1 -> openToActivityWithScroll(v, position)
            2 -> openToActivityWithScrollWaiting(v, position)
            3 -> openPagerActivity(v, position)
            4 -> openPagerActivityWithoutTransition(v, position)
        }
    }

    private fun openToActivity(view: View, position: Int) {
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            getString(R.string.shared_element)
        ).toBundle()
        startActivity(intent, option)
    }

    private fun openToActivityWithScroll(view: View, position: Int) {
        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
        }
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            getString(R.string.shared_element)
        ).toBundle()
        startActivity(intent, option)
    }

    private fun openToActivityWithScrollWaiting(view: View, position: Int) {
        val intent = ToActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            getString(R.string.shared_element)
        ).toBundle()

        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent, option)
            }, 200)
        } else {
            startActivity(intent, option)
        }
    }

    private fun openPagerActivity(view: View, position: Int) {
        val intent = PagerActivity.createIntent(activity as Context, position)
        val option = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            getString(R.string.shared_element)
        ).toBundle()

        if (shoulScrollList(view)) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent, option)
            }, 200)
        } else {
            startActivity(intent, option)
        }
    }

    private fun openPagerActivityWithoutTransition(view: View, position: Int) {
        val intent = PagerActivity.createIntent(activity as Context, position, false)
        val option = ActivityOptions.makeSceneTransitionAnimation(
            activity,
            view,
            getString(R.string.shared_element)
        ).toBundle()

        if (shoulScrollList(view)) {
            binding.list.smoothScrollToPosition(position)
            Handler(Looper.getMainLooper()).postDelayed({
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

    private fun scrollToCurrentPosition() {
        val position = PhotoStore.getCurrentPosition(activity as Context)
        binding.list.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                binding.list.removeOnLayoutChangeListener(this)
                val layoutManager = binding.list.layoutManager
                val viewAtPosition = layoutManager?.findViewByPosition(position)
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {
                    binding.list.post { layoutManager?.scrollToPosition(position) }
                }
            }
        })
    }

    companion object {
        fun newInstance() = FromFragment()
    }
}
