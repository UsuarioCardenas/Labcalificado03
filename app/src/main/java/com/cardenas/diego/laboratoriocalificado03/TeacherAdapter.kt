package com.cardenas.diego.laboratoriocalificado03

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cardenas.diego.laboratoriocalificado03.databinding.ItemTeacherBinding

class TeacherAdapter(
    private var teachers: List<Teacher>,
    private val onClick: (Teacher) -> Unit,
    private val onLongClick: (Teacher) -> Unit
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(private val binding: ItemTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(teacher: Teacher) {
            binding.txtName.text = teacher.name
            binding.txtSurname.text = teacher.last_name
            binding.txtEmail.text = teacher.email  // Asignamos el correo electrónico
            binding.txtPhone.text = teacher.phone  // Asignamos el teléfono

            Glide.with(binding.imgPhoto.context)
                .load(teacher.imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.imgPhoto)

            binding.root.setOnClickListener { onClick(teacher) }
            binding.root.setOnLongClickListener {
                onLongClick(teacher)
                true
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }


    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size

    fun updateData(newTeachers: List<Teacher>) {
        teachers = newTeachers
        notifyDataSetChanged()
    }
}