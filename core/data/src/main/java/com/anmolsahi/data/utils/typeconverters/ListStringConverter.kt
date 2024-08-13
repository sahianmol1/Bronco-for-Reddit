package com.anmolsahi.data.utils.typeconverters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringConverter {
    @TypeConverter
    fun fromStringList(value: String?): List<String?>? {
        return if (value == null) null else Json.decodeFromString(value)
    }

    @TypeConverter
    fun toStringList(list: List<String?>?): String? {
        return list?.let { Json.encodeToString(it) }
    }
}
