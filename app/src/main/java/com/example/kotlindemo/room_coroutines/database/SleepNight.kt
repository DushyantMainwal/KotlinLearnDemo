package com.example.kotlindemo.room_coroutines.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var sleepId: Long = 0,
    @ColumnInfo(name = "start_time_milli")
    var startTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time_milli")
    var stopTime: Long = startTime,
    @ColumnInfo(name = "quality_rating")
    var qualityIndex: Int = -1

)
