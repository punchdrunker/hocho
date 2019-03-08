package tokyo.punchdrunker.hocho.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import timber.log.Timber
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.databinding.FragmentTransitionDetailBinding

class TransitionDetailFragment : Fragment() {
    private lateinit var binding: FragmentTransitionDetailBinding
    private val images = arrayOf(R.drawable.img_cat, R.drawable.img_dog, R.drawable.img_parts, R.drawable.img_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionSet().addTransition(ChangeImageTransform())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransitionDetailBinding.inflate(inflater, container, false)
        val position = arguments?.getInt("position")
        val key = position!! % 4
        binding.photo.setImageResource(images[key])
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                Timber.w("names %s", names!![0])
                sharedElements!![names!![0]] = view!!.findViewById(R.id.photo)
            }
        })
        return binding.root
    }

    companion object {
        fun newInstance(position: Int): TransitionDetailFragment {
            val fragment = TransitionDetailFragment()
            val barg = Bundle()
            barg.putInt("position", position)
            fragment.arguments = barg
            return fragment
        }
    }
}
