package tokyo.punchdrunker.hocho

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tokyo.punchdrunker.hocho.databinding.FragmentTransitionBinding

class TransitionFragment : Fragment(), TransitionNavigator {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var binding: FragmentTransitionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransitionBinding.inflate(inflater, container, false)
        binding.list.adapter = PhotoAdapter(this)
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
        when (position %4) {
            0 -> openFragment(v, position)
            1 -> openActivity(v, position)
            2 -> {}
            3 -> {}
        }
    }



    fun openFragment (v: View, position: Int) {}

    fun openActivity(view: View, position: Int) {
        val intent = Intent(activity, TransitionDetailActivity::class.java)
        val option = ActivityOptions.makeSceneTransitionAnimation(activity, view, "transition").toBundle()
        startActivity(intent, option)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = TransitionFragment()
    }
}
