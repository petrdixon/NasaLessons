package com.example.nasalessons.ui.main.model

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.nasalessons.ui.main.view.PictureBeforeYesterdayFragment
import com.example.nasalessons.ui.main.view.PictureTodayFragment
import com.example.nasalessons.ui.main.view.PictureYesterdayFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
{

    override fun getItem(position: Int): Fragment {
        // устанавливаю фрагмент. Фото дня зависит от текущей позиции ViewPager
        return PictureBeforeYesterdayFragment.newInstance(GetDate().getDate(position))
    }
    override fun getCount(): Int {
        return 3 // TODO автоматом получать где-то количество страниц ViewPager
    }

}
