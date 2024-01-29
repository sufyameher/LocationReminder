package com.example.locationreminder.locationreminder.reminderitem

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.locationreminder.base.BaseViewModel
import com.example.locationreminder.locationreminder.data.ReminderDTO
import com.example.locationreminder.locationreminder.data.ReminderDataSource
import com.example.locationreminder.locationreminder.data.Result
import kotlinx.coroutines.launch

class RemindersListViewModel(
    app: Application,
    private val dataSource: ReminderDataSource
) : BaseViewModel(app) {
    // list that holds the reminder data to be displayed on the UI
    private val remindersList = MutableLiveData<List<ReminderDataItem>>()


    fun loadReminders() {
        showLoading.value = true
        viewModelScope.launch {
            showLoading.postValue(false)
            //interacting with the dataSource has to be through a coroutine
            val result = dataSource.getReminders()
            showLoading.value = false
            when (result) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<ReminderDataItem>()
                    dataList.addAll((result.data as List<ReminderDTO>).map { reminder ->
                        //map the reminder data from the DB to the be ready to be displayed on the UI
                        ReminderDataItem(
                            reminder.title,
                            reminder.description,
                            reminder.location,
                            reminder.latitude,
                            reminder.longitude,
                            reminder.id
                        )
                    })
                    remindersList.value = dataList
                }

                is Result.Error ->
                    showSnackBar.value = result.message
            }

            //check if no data has to be shown
            invalidateShowNoData()
        }
    }

    private fun invalidateShowNoData() {
        showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }
}