package ua.com.dekalo.hometask.ui.utils

import android.app.Activity
import android.transition.Fade
import android.transition.Transition
import android.view.View
import ua.com.dekalo.hometask.R

class AnimatedTransitionUtils {
    companion object {
        fun setupForAnimatedTransition(activity: Activity, transition: Transition = Fade()) {
            transition.excludeTarget(activity.window.decorView.findViewById<View>(R.id.action_bar_container), true)
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            transition.excludeTarget(android.R.id.navigationBarBackground, true)
            activity.window.exitTransition = transition
            activity.window.enterTransition = transition
        }
    }
}