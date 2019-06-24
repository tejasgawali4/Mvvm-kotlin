package com.techdivine.school.data.communication

import android.content.Context
import com.techdivine.school.App
import com.techdivine.school.R
import com.techdivine.school.data.models.Movies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver

class MovieRepository(var context: Context, var dataListener : DataListener) {
    interface DataListener {
        fun onError(message: String)
        fun onComplete()
        fun onSuccess(value: ArrayList<Movies>?)
    }

    private var factory: ServiceFactory = ServiceFactory()
    private var api: ApiService = factory.provideApi(true)

    fun loadMovies(): DisposableObserver<ArrayList<Movies>>? {
        return api.getTrips()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(App.instance?.defaultSubscribeScheduler())
            .subscribeWith(LongOperationObserver())
    }

    private inner class LongOperationObserver: DisposableObserver<ArrayList<Movies>>() {

        override fun onComplete() {
            dataListener.onComplete()
        }

        override fun onError(e: Throwable?) {
            dataListener.onError(context.getString(R.string.error_getting_movies))
        }

        override fun onNext(value: ArrayList<Movies>?) {
            dataListener.onSuccess(value)
        }
    }

}