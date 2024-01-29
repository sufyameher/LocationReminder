package com.example.locationreminder.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewBindingViewHolder<T, VB : ViewBinding>(binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        // Implement your binding logic using the View Binding instance
    }

    companion object {
        // Add a utility function to create the ViewHolder with View Binding
        inline fun <reified T, reified VB : ViewBinding> create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): ViewBindingViewHolder<T, VB> {
            val binding = VB::class.java.getMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.java
            ).invoke(null, inflater, parent, false) as VB
            return ViewBindingViewHolder(binding)
        }
    }
}

