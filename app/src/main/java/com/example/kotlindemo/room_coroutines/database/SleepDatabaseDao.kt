package com.example.kotlindemo.room_coroutines.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Insert
    fun insertData(sleepNight: SleepNight)

    @Update
    fun updateData(sleepNight: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE sleepId = :idKey")
    fun get(idKey: Long): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY sleepId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>

    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY sleepId DESC LIMIT 1")
    fun getTonight(): SleepNight?

//    @Delete
//    fun deleteData(key: Long) {
//    }
}