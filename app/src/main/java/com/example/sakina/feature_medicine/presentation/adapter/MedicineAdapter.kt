package com.example.sakina.feature_medicine.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sakina.R
import com.example.sakina.core.util.Constant
import com.example.sakina.databinding.MedicineListItemBinding
import com.example.sakina.feature_medicine.domain.model.Medicine

class MedicineAdapter : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class MedicineViewHolder(private val binding: MedicineListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine) {
            binding.apply {
                txtMedicineName.text = medicine.name

                val imageId = when (medicine.imageId) {
                    Constant.PILL_1 -> R.drawable.ic_pill1
                    else -> R.drawable.ic_pill2
                }

                Glide.with(root).load(imageId).into(imgMedicine)

                root.setOnClickListener {
                    onItemClickListener?.invoke(medicine)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding =
            MedicineListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = differ.currentList[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Medicine) -> Unit)? = null

    fun setOnItemClickListener(listener: (Medicine) -> Unit) {
        onItemClickListener = listener
    }


}
