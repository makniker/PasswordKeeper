package com.example.passwordkeeper.data.network

import com.google.gson.annotations.SerializedName

data class FaviconCloudModel(
    @SerializedName("hasIcon") val hasIcon: Boolean,
    @SerializedName("icon") val icon: String,
    @SerializedName("format") val format: String
)