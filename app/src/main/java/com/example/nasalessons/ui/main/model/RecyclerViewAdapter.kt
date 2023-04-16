package com.example.nasalessons.ui.main.model

import android.graphics.Color
import android.graphics.Typeface.BOLD
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasalessons.R
import com.example.nasalessons.ui.main.view.AnimationsFragment.*
import com.google.android.material.internal.ContextUtils.getActivity

class RecyclerViewAdapter(

    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<DataNote>


) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // ViewHolder - здесь кэшируется view. Один тип списка - ViewHolder
        // parent: ViewGroup - куда добавляем список
        // int viewType - тип отображаемого элемента списка. В списке могут быть разные типы.
        val inflater = LayoutInflater.from(parent.context) // откуда будем надувать (из полученного выше параметра parent)
        return MyViewHolder(
            inflater.inflate(R.layout.recycler_note, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyViewHolder // говорим какой свой ViewHolder будет использован вместо заводского holder
        holder.bind(data[position]) // передается определенный элемент списка, соответствующий текущей отображаемой позиции

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: DataNote) {
            // связь данных с элементами. Действие. Например, в какой элемент добавить текст
//            itemView.findViewById<TextView>(R.id.noteHeader).text = data.noteHeader

            // применение SpannableString
            val spannable = SpannableString(data.noteHeader)
            spannable.setSpan(
                ForegroundColorSpan(Color.RED),
                8, 12,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(
                StyleSpan(BOLD),
                8, spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            itemView.findViewById<TextView>(R.id.noteHeader).text = spannable


            // обработка нажатий элементов RecyclerView
            // itemView это view конкретной строки RecyclerView. Можно работать именно с ее элементами
            itemView.findViewById<ImageView>(R.id.imageNoteIcon).setOnClickListener {
                onListItemClickListener.onItemClick(data)

            }
//             добавление и удаление элементов
            itemView.findViewById<ImageView>(R.id.imageAdd).setOnClickListener {
                addItem()
            }
            itemView.findViewById<ImageView>(R.id.imageDelete).setOnClickListener {
                removeItem()
            }
            itemView.findViewById<ImageView>(R.id.buttonUp).setOnClickListener {
                moveItemUp()
            }
            itemView.findViewById<ImageView>(R.id.buttonDown).setOnClickListener {
                moveItemDown()
            }

            // обработка тапа по заметке. Открываю CardView для правки текстов
            itemView.findViewById<AppCompatTextView>(R.id.noteHeader).setOnClickListener {
                itemView.findViewById<CardView>(R.id.editNotes2).visibility = View.VISIBLE
                itemView.findViewById<AppCompatEditText>(R.id.editHeader).setText(data.noteHeader)
                itemView.findViewById<AppCompatEditText>(R.id.editNote).setText(data.noteText)
            }
            // обработка закрытия CardView
            itemView.findViewById<ImageView>(R.id.imageCloseCardView).setOnClickListener {
                itemView.findViewById<CardView>(R.id.editNotes2).visibility = View.GONE
            }
            // обработка сохранения текстов
            itemView.findViewById<ImageView>(R.id.imageSubmitCardView).setOnClickListener {
                val header = itemView.findViewById<AppCompatEditText>(R.id.editHeader).text.toString()
                val text = itemView.findViewById<AppCompatEditText>(R.id.editNote).text.toString()
                addNoteText(adapterPosition, header, text)
                itemView.findViewById<CardView>(R.id.editNotes2).visibility = View.GONE
            }
        }

        private fun addNoteText(index: Int, noteHeader: String, noteText: String) {
            data[index] = DataNote(noteHeader, noteText)
            notifyItemChanged(index)
        }
        // добавление строки
        private fun addItem() {
            data.add(0, DataNote("Новая заметка", ""))
            notifyItemInserted(0) // оповещение адаптера, что нужно добавить элемент. Обновляет 1 элемент
//            notifyDataSetChanged() // обновляет список полностью, не будет анимации
        }
        // удаление строки
        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition) // оповещение адаптера, что нужно удалить элемент. Обновляет 1 элемент
        }
        // перемещение наверх
        private fun moveItemUp() {
            if (layoutPosition != 0)
                notifyItemMoved(layoutPosition, layoutPosition - 1)
        }
        // перемещение вниз
        private fun moveItemDown() {
            if (layoutPosition != itemCount - 1)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
        }
    }
}