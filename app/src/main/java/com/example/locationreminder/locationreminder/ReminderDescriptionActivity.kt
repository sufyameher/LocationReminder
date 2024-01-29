package com.example.locationreminder.locationreminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.locationreminder.databinding.ActivityReminderDescriptionBinding
import com.example.locationreminder.locationreminder.reminderitem.ReminderDataItem

/**
 * Activity that displays the reminder details after the user clicks on the notification
 */
class ReminderDescriptionActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ReminderDataItem = "EXTRA_Reminder_Data_Item"

        //        receive the reminder object after the user clicks on the notification
        fun newIntent(context: Context, reminderDataItem: ReminderDataItem): Intent {
            val intent = Intent(context, ReminderDescriptionActivity::class.java)
            intent.putExtra(EXTRA_ReminderDataItem, reminderDataItem)
            return intent
        }
    }

    private lateinit var binding: ActivityReminderDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReminderDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val reminderDataItem = intent.getParcelableExtra<ReminderDataItem>(EXTRA_ReminderDataItem)
//
//        reminderDataItem?.let {
//            binding.titleTextview.text = it.title
//            binding.descriptionTextView.text = it.description
//            binding.locationTextView.text = it.location
//            val coordinatesText = "Latitude: ${it.latitude}, Longitude: ${it.longitude}"
//            binding.coordinatesTextView.text = coordinatesText
//        }
    }
}