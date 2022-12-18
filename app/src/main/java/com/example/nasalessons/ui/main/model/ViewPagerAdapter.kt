package com.example.nasalessons.ui.main.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasalessons.ui.main.view.MainActivity
import com.example.nasalessons.ui.main.view.`Pictures3DaysFragment`

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{

    override fun getItem(position: Int): Fragment {
        // устанавливаю фрагмент. Фото дня зависит от текущей позиции ViewPager
        return Pictures3DaysFragment.newInstance(GetDate().getDate(position))
    }
    override fun getCount(): Int {
        return 3
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }


//    companion object {
//        val currentPositionViewPager = ViewPagerAdapter().count
//    }
}
