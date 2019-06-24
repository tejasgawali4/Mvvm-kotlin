package com.techdivine.school.views.activities.notification

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.techdivine.school.R
import com.techdivine.school.data.models.CacheData
import com.techdivine.school.viewmodels.notifications.MoviesViewModel
import com.techdivine.school.views.activities.BaseActivity
import com.techdivine.school.databinding.MoviesActivityBinding
import com.techdivine.school.views.adapters.MovieListAdapter
import com.techdivine.school.views.adapters.MovieListAdapter.EventListener
import kotlinx.android.synthetic.main.movies_activity.*

class MoviesActivity : BaseActivity(), EventListener {

    private lateinit var mMoviesViewModel: MoviesViewModel
    private lateinit var mMoviesBinding : MoviesActivityBinding
    var adapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()

        MovieListRv.layoutManager= LinearLayoutManager(this)
        adapter= MovieListAdapter(this,this)
        adapter?.setMovies(CacheData.movies)
        MovieListRv.adapter=adapter
        supportActionBar?.title=getString(R.string.movie_list_screen)
    }

    private fun initViewModel() {
        mMoviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        mMoviesBinding = DataBindingUtil.setContentView(this, R.layout.movies_activity)
        mMoviesBinding.viewModel = mMoviesViewModel
        mMoviesViewModel.loadTrips()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMoviesViewModel.destroy()
    }

    override fun onResume() {
        super.onResume()
        mMoviesViewModel.loadTrips()

    }

    override fun onSubmitSelected(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onEmployeeSelected(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}