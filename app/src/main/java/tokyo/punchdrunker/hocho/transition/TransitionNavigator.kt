package tokyo.punchdrunker.hocho.transition

import android.view.View

interface TransitionNavigator {
    fun transition(v: View, position: Int)
}