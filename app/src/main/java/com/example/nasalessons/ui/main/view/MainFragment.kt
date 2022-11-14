package com.example.nasalessons.ui.main.view

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.nasalessons.R
import com.example.nasalessons.databinding.MainFragmentBinding
import com.example.nasalessons.ui.main.model.ModelRetrofitNasa
import com.example.nasalessons.ui.main.model.ViewPagerAdapter
import com.example.nasalessons.ui.main.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_fragment.*
import me.relex.circleindicator.CircleIndicator


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    val settingsRealFragment = SettingsRealFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager) // установка ViewPager

        val indicator = view.findViewById(R.id.indicator) as CircleIndicator // установка индикатора перемотки (точек)
        indicator.setViewPager(binding.viewPager)

        // скрыл BottomAppBar чтобы не мешал
        bottom_app_bar.visibility = View.GONE
        fab.visibility = View.GONE

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container)) // BottomSheet
        fab.setOnClickListener { // показывать BottomSheet по нажатию fab
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        setBottomAppBar(view) // показать кнопки Избранное и Настройки в BottomAppBar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // метод для управления BottomSheet
    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.halfExpandedRatio = 0.6f
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            R.id.app_bar_settings -> {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, settingsRealFragment)
                        .commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // кнопки Избранное и Настройки в BottomAppBar
    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
    }

    companion object {
        fun newInstance() = MainFragment()
        var instance: MainFragment? = null

    }

}


