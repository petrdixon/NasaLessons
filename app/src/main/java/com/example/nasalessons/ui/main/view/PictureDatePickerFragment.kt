package com.example.nasalessons.ui.main.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.icu.util.Calendar
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nasalessons.R
import com.example.nasalessons.databinding.FragmentPictureDatePickerBinding
import com.example.nasalessons.ui.main.model.ModelRetrofitNasa
import com.example.nasalessons.ui.main.viewmodel.MainViewModel
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.behavior.SwipeDismissBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_picture_date_picker.*
import kotlinx.android.synthetic.main.fragment_picture_date_picker.date_value
import kotlinx.android.synthetic.main.fragment_picture_date_picker.description
import kotlinx.android.synthetic.main.fragment_pictures_3days.*
import java.time.LocalDate


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PictureDatePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class PictureDatePickerFragment : Fragment() {
    private var _binding: FragmentPictureDatePickerBinding? = null
    private val binding get() = _binding!!

    var needDate: String = LocalDate.now().toString()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_picture_date_picker, container, false)
        _binding = FragmentPictureDatePickerBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // обработка выбора даты
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()
        datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH), // переменные, которые принимает .init
            today.get(Calendar.DAY_OF_MONTH)
        )
        { _, year, month, day -> // тело функции .init
            val month = month + 1
            needDate = "$year-$month-$day" // 2022-12-10

            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        // смахивание CardView
        val swipe: SwipeDismissBehavior<CardView?> = SwipeDismissBehavior<CardView?>()
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY)
        swipe.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View?) { // то, что происходит после смахивания CardView
                Toast.makeText(view?.context, "Card swiped !!", Toast.LENGTH_SHORT).show()
            }
            override fun onDragStateChanged(state: Int) {}
        }
        val mCardView = swipe_card.findViewById<CardView>(R.id.swipe_card)
        val coordinatorParams = mCardView.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorParams.behavior = swipe


        // по кнопке посылаю запрос в nasa и показываю полученные данные
        buttonStartRequest.setOnClickListener {
            // подписываюсь на изменения liveData, передаю нужную дату
            viewModel.getDataNasaFromViewModel(needDate)
                .observe(viewLifecycleOwner) { renderData(it) }
        }
    }

    private fun renderData(data: Any) {
        data as ModelRetrofitNasa
        date_value.text = "Picture of day: $needDate"
        // вывод текста NASA
        description.text = data.explanation
        description.movementMethod = ScrollingMovementMethod()
        // вывод картинки
        Picasso
            .get()
            .load(data.url)
            .into(binding.pictureView)

        date_value.text = "Picture of day: $needDate"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PictureDatePickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PictureDatePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}