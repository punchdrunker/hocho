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

class TransitionFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var binding: FragmentTransitionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTransitionBinding.inflate(inflater, container, false)
        binding.list.adapter = PhotoAdapter()
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

    fun openActivity(view: View) {
        val intent = Intent(activity, TransitionDetailActivity::class.java)
        startActivity(intent)
    }

    fun openActivityWithTransition(view: View) {
//        val icon = binding.icon
//        val intent = Intent(activity, TransitionDetailActivity::class.java)
//        val option = ActivityOptions.makeSceneTransitionAnimation(activity, icon, "transition_icon").toBundle()
//        startActivity(intent, option)
    }

    fun openFragmentWithTransition (view: View) {}

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = TransitionFragment()
    }
}
