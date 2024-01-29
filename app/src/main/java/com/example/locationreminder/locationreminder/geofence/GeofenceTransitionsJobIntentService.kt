package com.example.locationreminder.locationreminder.geofence

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.example.locationreminder.locationreminder.data.ReminderDTO
import com.example.locationreminder.locationreminder.data.ReminderDataSource
import com.example.locationreminder.locationreminder.data.Result
import com.example.locationreminder.locationreminder.reminderitem.ReminderDataItem
import com.example.locationreminder.locationreminder.savereminder.SaveReminderFragment.Companion.ACTION_GEOFENCE_EVENT
import com.example.locationreminder.utils.sendNotification
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob



    companion object {
        private const val JOB_ID = 573
        private const val TAG = "GeofenceIntentSer"


        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent
            )
        }
    }

    override fun onHandleWork(intent: Intent) {

        if (intent.action == ACTION_GEOFENCE_EVENT) {
            val geofencingEvent = GeofencingEvent.fromIntent(intent)

            if (geofencingEvent != null) {
                if (geofencingEvent.hasError()) {
                    val errorMessage = errorMessage(this, geofencingEvent.errorCode)
                    Log.e(TAG, errorMessage)
                    return
                }
            }

            if (geofencingEvent != null) {
                if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    geofencingEvent.triggeringGeofences?.let { sendNotification(it) }
                    Log.d(TAG, "Geofences found")
                }
            }
        }
    }

    private fun sendNotification(triggeringGeofences: List<Geofence>) {

        for (triggeringGeofence in triggeringGeofences) {
            val requestId = triggeringGeofence.requestId

            //Get the local repository instance
            val remindersLocalRepository: ReminderDataSource by inject()
//        Interaction to the repository has to be through a coroutine scope
            CoroutineScope(coroutineContext).launch(SupervisorJob()) {
                //get the reminder with the request id
                val result = remindersLocalRepository.getReminder(requestId)
                if (result is Result.Success<ReminderDTO>) {
                    val reminderDTO = result.data
                    //send a notification to the user with the reminder details
                    sendNotification(
                        this@GeofenceTransitionsJobIntentService, ReminderDataItem(
                            reminderDTO.title,
                            reminderDTO.description,
                            reminderDTO.location,
                            reminderDTO.latitude,
                            reminderDTO.longitude,
                            reminderDTO.id
                        )
                    )
                }
            }
        }
    }
}