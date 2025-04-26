package com.android.data.util

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromTypeList(types: List<String>?): String? {
        return types?.joinToString(",")
    }

    @TypeConverter
    fun toTypeList(typesString: String?): List<String>? {
        return typesString?.split(",") ?: emptyList()
    }
}
