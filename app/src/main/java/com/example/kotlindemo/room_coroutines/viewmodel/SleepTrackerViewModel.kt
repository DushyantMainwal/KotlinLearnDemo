package com.example.kotlindemo.room_coroutines.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.kotlindemo.room_coroutines.database.SleepDatabaseDao
import com.example.kotlindemo.room_coroutines.database.SleepNight
import com.example.kotlindemo.room_coroutines.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    /*
    This viewModelJob allows you to cancel all coroutines started by this view model when the view model is no longer used and
    is destroyed. This way, you don't end up with coroutines that have nowhere to return to.
     */
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var tonight = MutableLiveData<SleepNight?>()
    private val nightsList = database.getAllNights()
    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    private val clearSnackBarShowed = MutableLiveData<Boolean>()

    //The Start button should be enabled when tonight is null.
    val startButtonVisible = Transformations.map(tonight) {
        it == null
    }

    //The Stop button should be enabled when tonight is not null.
    val stopButtonVisible = Transformations.map(tonight) {
        it != null
    }

    //The Clear button should only be enabled if nights, and thus the database, contains sleep nights.
    val clearButtonVisible = Transformations.map(nightsList) {
        it?.isNotEmpty()
    }

    init {
        initializeTonight()
    }

    val nightsString = Transformations.map(nightsList) { nights ->
        formatNights(nights, application.resources)
    }

    private fun initializeTonight() {
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.stopTime != night?.startTime) {
                night = null
            }
            night
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val sleepNight = SleepNight()
            insertData(sleepNight)
            tonight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insertData(sleepNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insertData(sleepNight)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.stopTime = System.currentTimeMillis()
            updateData(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun updateData(sleepNight: SleepNight) {
        withContext(Dispatchers.IO) {
            database.updateData(sleepNight)
        }
    }

    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun onClearData() {
        uiScope.launch {
            clear()
            tonight.value = null
            clearSnackBarShowed.value = true
        }
    }

    val showSnackBarEvent: LiveData<Boolean> = clearSnackBarShowed

    fun doneSnackBarShowing() {
        clearSnackBarShowed.value = false
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    override fun onCleared() {
        println("onCleared")
        super.onCleared()
        viewModelJob.cancel()
    }
}