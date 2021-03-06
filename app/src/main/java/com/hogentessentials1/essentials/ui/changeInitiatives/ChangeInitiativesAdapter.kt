package com.hogentessentials1.essentials.ui.changeInitiatives

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogentessentials1.essentials.data.model.ChangeInitiative
import com.hogentessentials1.essentials.databinding.ChangeinitiativesItemBinding

/**
 * Converts Change Initiatives to views
 * @author Ziggy Moens
 * @author Simon De Wilde
 * @param clickListener listener for when a change initiative is tapped
 */
class ChangeInitiativeAdapter(val clickListener: ChangeInitiativesListener) :
    ListAdapter<ChangeInitiative, ChangeInitiativeAdapter.ViewHolder>(ChangeInitiativeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ChangeinitiativesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: ChangeInitiativesListener, item: ChangeInitiative) {
            binding.changeInitiative = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ChangeinitiativesItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ChangeInitiativeDiffCallback : DiffUtil.ItemCallback<ChangeInitiative>() {
    override fun areItemsTheSame(oldItem: ChangeInitiative, newItem: ChangeInitiative): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ChangeInitiative, newItem: ChangeInitiative): Boolean {
        return oldItem == newItem
    }
}

/**
 * @author Ziggy Moens
 * listener for when a change initiative is tapped
 * @param clickListener
 */
class ChangeInitiativesListener(val clickListener: (changeInitiative: ChangeInitiative) -> Unit) {
    fun onClick(changeInitiative: ChangeInitiative) = clickListener(changeInitiative)
}
