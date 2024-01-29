package com.example.locationreminder.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding>(
    private val callback: ((item: T) -> Unit)? = null
) : RecyclerView.Adapter<ViewBindingViewHolder<T, VB>>() {

    private var _items: MutableList<T> = mutableListOf()

    /**
     * Returns the _items data
     */
    private val items: List<T>?
        get() = this._items

    override fun getItemCount() = _items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder<T, VB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = inflateBinding(layoutInflater, parent, viewType)
        return ViewBindingViewHolder(binding)
    }


    override fun onBindViewHolder(holder:  ViewBindingViewHolder<T, VB>, position: Int) {
        val item = getItem(position)
        //holder.bind()
        holder.itemView.setOnClickListener {
            callback?.invoke(item)
        }
    }

    private fun getItem(position: Int) = _items[position]

    /**
     * Adds data to the actual Dataset
     *
     * @param items to be merged
     */
    fun addData(items: List<T>) {
        _items.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Clears the _items data
     */
    fun clear() {
        _items.clear()
        notifyDataSetChanged()
    }

//    abstract fun createBinding(parent: ViewGroup): VB

    abstract fun getLayoutRes(viewType: Int): Int


    open fun getLifecycleOwner(): androidx.lifecycle.LifecycleOwner? {
        return null
    }

    abstract fun inflateBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VB }

//    inner class ViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(item: T) {
//            // Implement binding logic here
//        }
//    }
//}
