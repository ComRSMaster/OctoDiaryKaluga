package org.bxkr.octodiary.models.events


import com.google.gson.annotations.SerializedName

data class Grade(
    @SerializedName("five")
    val five: Double,
    @SerializedName("hundred")
    val hundred: Double,
    @SerializedName("origin")
    val origin: String
)