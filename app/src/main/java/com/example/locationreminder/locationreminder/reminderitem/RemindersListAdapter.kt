package com.example.locationreminder.locationreminder.reminderitem

import com.example.locationreminder.R
import com.example.locationreminder.base.BaseRecyclerViewAdapter
import com.example.locationreminder.databinding.ItReminderBinding

abstract class RemindersListAdapter(callBack: (selectedReminder: ReminderDataItem) -> Unit) :
    BaseRecyclerViewAdapter<ReminderDataItem, ItReminderBinding>(callBack) {

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.it_reminder
    }
}

//class RemindersListAdapter(
//    private val callBack: (selectedReminder: ReminderDataItem) -> Unit
//) : BaseRecyclerViewAdapter<ReminderDataItem, ItReminderBinding>(callBack) {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewBindingViewHolder<ReminderDataItem, ItReminderBinding> {
//        val inflater = LayoutInflater.from(parent.context)
//        return ViewBindingViewHolder.create(inflater, parent)
//    }
//
//    override fun onBindViewHolder(
//        holder: ViewBindingViewHolder<ReminderDataItem, ItReminderBinding>,
//        position: Int
//    ) {
//        val item = getItem(position)
//        holder.bind(item)
//
//        // Call the callback when an item is clicked
//        holder.itemView.setOnClickListener {
//            callBack.invoke(item)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return reminderList.size
//    }
//
//    private fun getItem(position: Int): ReminderDataItem {
//        return reminderList[position]
//    }
//}