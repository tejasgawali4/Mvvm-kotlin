package com.techdivine.school.data.models
import com.google.gson.annotations.SerializedName

data class Movies(
    @SerializedName("c_id")
    val cId: Int,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("m_banner_url")
    val mBannerUrl: String,
    @SerializedName("m_id")
    val mId: Int,
    @SerializedName("m_name")
    val mName: String,
    @SerializedName("url")
    val url: String
)