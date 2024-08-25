package com.anmolsahi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val timestamp: Long = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
)
