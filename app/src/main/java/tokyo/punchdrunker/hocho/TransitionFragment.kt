package tokyo.punchdrunker.hocho

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionSet
import tokyo.punchdrunker.hocho.databinding.FragmentTransitionBinding

class TransitionFragment : Fragment(), TransitionNavigator {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var binding: FragmentTransitionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransitionBinding.inflate(inflater, container, false)
        binding.list.adapter = PhotoAdapter(this)
        exitTransition = TransitionSet().addTransition(ChangeImageTransform())
        enterTransition = TransitionSet().addTransition(ChangeImageTransform())

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun transition(v: View, position: Int) {
        when (position % 4) {
            0 -> openFragment(v, position)
            1 -> openActivity(v, position)
            2 -> {
            }
            3 -> {
            }
        }
    }


    fun openFragment(v: View, position: Int) {
        val fragment = TransitionDetailFragment.newInstance(position)
        val ts = TransitionSet().addTransition(ChangeImageTransform())
        fragment.enterTransition = ts
        fragment.sharedElementEnterTransition = ts
        fragment.exitTransition = ts
        fragment.sharedElementReturnTransition = ts
        val sharedView = v.findViewById<ImageView>(R.id.card_photo)
        (exitTransition as TransitionSet).excludeTarget(sharedView, true)

        fragmentManager?.beginTransaction()?.setReorderingAllowed(true)?.
                addToBackStack(null)?.addSharedElement(sharedView, getString(R.string.shared_element))?.replace(R.id.fragment_container, fragment, TransitionDetailFragment::class.java.simpleName)?.commit()
    }

    fun openActivity(view: View, position: Int) {
        val intent = Intent(activity, TransitionDetailActivity::class.java)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.shared_element)).toBundle()
        startActivity(intent, option)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = TransitionFragment()
    }
}
