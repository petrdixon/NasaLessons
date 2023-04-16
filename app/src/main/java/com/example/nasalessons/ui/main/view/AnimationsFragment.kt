package com.example.nasalessons.ui.main.view

import android.os.Bundle
import android.transition.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.nasalessons.R
import com.example.nasalessons.databinding.FragmentAnimationsBinding
import com.example.nasalessons.ui.main.model.DataNote
import com.example.nasalessons.ui.main.model.OnListItemClickListener
import com.example.nasalessons.ui.main.model.RecyclerViewAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AnimationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AnimationsFragment : Fragment() {

    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!

    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_animations, container, false)
        _binding = FragmentAnimationsBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // увеличение картинки при нажатии
        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.container, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else
                    ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            binding.imageView.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else
                    ImageView.ScaleType.FIT_CENTER
        }
        // анимация впращения текста вокруг своей оси
        binding.textView.setOnClickListener {
            binding.textView.animate().rotationBy(30F).duration = 300
        }

        // RecyclerView
        // создаю массив, из которого при старте получается дефолтный список заметок
        val data = arrayListOf(
            DataNote("В западной традиции рыбой выступает фрагмент латинского текста из философского трактата Цицерона",
                "Description note 1"),
            DataNote("Header note 2", "Description note 2")
        )
        // прицепляю адаптер на RecyclerView
        binding.recyclerView.adapter = RecyclerViewAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(data: DataNote) {
                    Toast.makeText(context, data.noteHeader, Toast.LENGTH_SHORT).show()
                }
            },
            data // передаю массив
        )
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = AnimationsFragment()

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            AnimationsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    } // чтобы удалить старые ссылки на view

}