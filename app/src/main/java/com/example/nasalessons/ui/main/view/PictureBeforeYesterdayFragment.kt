package com.example.nasalessons.ui.main.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nasalessons.R
import com.example.nasalessons.databinding.FragmentPictureBeforeYesterdayBinding
import com.example.nasalessons.ui.main.model.GetDate
import com.example.nasalessons.ui.main.model.ModelRetrofitNasa
import com.example.nasalessons.ui.main.viewmodel.MainViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_picture_before_yesterday.*
import java.text.SimpleDateFormat
import java.util.*

private const val NEED_DATE_PARAM1 = "needDateParam"

class PictureBeforeYesterdayFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }
    private var needDate: String = GetDate().getDate(2)
    private var _binding: FragmentPictureBeforeYesterdayBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_picture_before_yesterday, container, false)
        _binding = FragmentPictureBeforeYesterdayBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // достаю сохраненную нужную дату, полученную через newInstance
        arguments?.let {
            needDate = it.getString(NEED_DATE_PARAM1).toString()
        }
        // подписываюсь на изменения liveData, передаю нужную дату
        viewModel.getDataNasaFromViewModel(needDate)
            .observe(viewLifecycleOwner) { renderData(it) }

    }

    private fun renderData(data: Any) {
        data as ModelRetrofitNasa

        // вывод текста
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
        @JvmStatic
        fun newInstance(needDate: String) =
            PictureBeforeYesterdayFragment().apply {
                arguments = Bundle().apply {
                    putString(NEED_DATE_PARAM1, needDate)
                }
            }
    }
}