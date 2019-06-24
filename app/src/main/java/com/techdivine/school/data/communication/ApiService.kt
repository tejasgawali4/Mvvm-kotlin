package com.techdivine.school.data.communication

import com.techdivine.school.data.models.Movies
import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("API/data.json")
    fun getTrips(): Observable<ArrayList<Movies>>
}