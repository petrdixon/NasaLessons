package com.example.nasalessons.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import com.example.nasalessons.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.main_fragment.*


class SettingsRealFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_real, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipLightTheme: Chip = requireView().findViewById(R.id.chipLight)
        val chipDarkTheme: Chip = requireView().findViewById(R.id.chipDark)

        chipLightTheme.setOnClickListener {
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putBoolean("LightTheme", true)?.apply()
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putBoolean("DarkTheme", false)?.apply()
            activity?.recreate()
        }
        chipDarkTheme.setOnClickListener {
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putBoolean("LightTheme", false)?.apply()
            activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putBoolean("DarkTheme", true)?.apply()
            activity?.recreate()
        }


        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container)) // BottomSheet
        fab.setOnClickListener { // показывать BottomSheet по нажатию fab
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
        setBottomAppBar(view) // показать кнопки Избранное и Настройки в BottomAppBar
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
        val mainFragment1 = MainFragment()
        when (item.itemId) {
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            // при нажатии на settings показываем mainFragment
            R.id.app_bar_settings -> {
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, mainFragment1)?.commit()
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

}