package tokyo.punchdrunker.hocho.transition


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.FragmentPagerItemBinding

private const val POSITION = "POSITION"

class PagerItemFragment : Fragment() {
    lateinit var binding: FragmentPagerItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager_item, container, false)
        var position = 0
        arguments
        if (arguments != null) {
            position = arguments!!.getInt(POSITION, 0)
        }
        val resourceId = PhotoStore.getImage(position)

        binding.photo.setImageResource(resourceId)

        return binding.root
    }


    companion object {
        fun newInstance(position: Int): PagerItemFragment {
            val fragment = PagerItemFragment()
            val argument = Bundle()
            argument.putInt(POSITION, position)
            fragment.arguments = argument
            return fragment
        }
    }
}
