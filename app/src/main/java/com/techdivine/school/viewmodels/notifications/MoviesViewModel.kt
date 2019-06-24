package com.techdivine.school.viewmodels.notifications

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.techdivine.school.data.communication.MovieRepository
import com.techdivine.school.data.models.Movies
import io.reactivex.observers.DisposableObserver

class MoviesViewModel (application: Application) : AndroidViewModel(application) , MovieRepository.DataListener {

    var ctx=application
    private var disposable: DisposableObserver<ArrayList<Movies>>? = null
    var tripsUpdated = MutableLiveData<Boolean>()
   private var tripRespository: MovieRepository = MovieRepository(ctx, this)
    var isProgress: ObservableBoolean = ObservableBoolean(false)
    var isDataAvailable: ObservableBoolean = ObservableBoolean(true)
    var isEmpty: ObservableBoolean = ObservableBoolean(true)

    fun loadTrips() {
        isProgress.set(true)
        isDataAvailable.set(true)
        disposable  = tripRespository.loadMovies()
    }

    fun destroy() {
        if(disposable!=null) {
            if (!disposable!!.isDisposed) disposable!!.dispose()
        }
        disposable = null
    }

    override fun onError(message: String) {
        isProgress.set(false)
        isDataAvailable.set(false)
    }

    override fun onComplete() {
        isProgress.set(false)
    }

    override fun onSuccess(value: ArrayList<Movies>?) {
        if(value==null){
            isDataAvailable.set(false)
        }else if(value.size==0){
            isDataAvailable.set(false)
        }else{

            isDataAvailable.set(true)
        }
    }
}