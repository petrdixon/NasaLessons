package com.example.nasalessons.ui.main.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nasalessons.R
import com.example.nasalessons.databinding.MainFragmentBinding
import com.example.nasalessons.ui.main.model.ModelRetrofitNasa
import com.example.nasalessons.ui.main.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

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

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container)) // BottomSheet
        fab.setOnClickListener { // показывать BottomSheet по нажатию fab
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        setBottomAppBar(view) // показать кнопки Избранное и Настройки в BottomAppBar

        viewModel.getDataNasaFromViewModel() // подписываюсь на изменения liveData получение картинки
            .observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun renderData(data: Any) {
        data as ModelRetrofitNasa
        bottomSheetDescription.text = data.explanation

        Picasso
            .get()
            .load(data.url)
            .into(binding.pictureOfDayView);
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


}


