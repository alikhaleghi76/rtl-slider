package ali.khaleghi.rtlintroslider.adapter

import ali.khaleghi.rtlintroslider.fragment.SampleFragment
import android.graphics.Typeface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, var isRtl: Boolean) : FragmentPagerAdapter(fragmentManager, 0) {

    private val fragmentList = ArrayList<Fragment>()
    private var typeface: Typeface? = null

    override fun getItem(position: Int): Fragment {
        return if (isRtl) fragmentList[fragmentList.size - position - 1]
        else fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun addFragmentList(fragments: ArrayList<Fragment>) {
        fragmentList.addAll(fragments)
        for (fragment in fragments) {
            if (typeface != null && fragment is SampleFragment)
                fragment.setTypeface(typeface)
        }
    }

    fun addFragment(fragment: Fragment) {
        if (typeface != null && fragment is SampleFragment)
            fragment.setTypeface(typeface)

        fragmentList.add(fragment)
    }

    fun removeFragment(position: Int) {
        fragmentList.removeAt(position)
    }

    fun removeAllFragments() {
        fragmentList.clear()
    }

    fun setTypeface(typeface: Typeface) {
        this.typeface = typeface
        for (fragment in fragmentList) {
            if (fragment is SampleFragment)
                fragment.setTypeface(typeface)
        }
    }

}
