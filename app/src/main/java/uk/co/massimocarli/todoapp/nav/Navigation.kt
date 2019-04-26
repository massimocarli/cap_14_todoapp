package uk.co.massimocarli.todoapp.nav

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import uk.co.massimocarli.todoapp.R

interface Navigation {

    fun showFragment(frag: Fragment)

    fun showFragmentWithBackStack(frag: Fragment, backStackTag: String? = null)

    fun back()
}

class NavigationImpl(val activity: FragmentActivity) : Navigation {

    override fun showFragment(frag: Fragment) {
        showFragmentWithBackStack(frag)
    }

    override fun showFragmentWithBackStack(frag: Fragment, backStackTag: String?) {
        with(activity.supportFragmentManager) {
            with(beginTransaction()) {
                backStackTag?.run {
                    this@with.addToBackStack(this)
                }
                replace(R.id.anchor, frag)
                commit()
            }
        }
    }

    override fun back() {
        activity.supportFragmentManager.popBackStack()
    }
}