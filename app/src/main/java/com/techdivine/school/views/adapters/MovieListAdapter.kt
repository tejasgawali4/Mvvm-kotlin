package com.techdivine.school.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techdivine.school.R
import com.techdivine.school.data.models.Movies
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlin.collections.ArrayList

class MovieListAdapter(val context: Context, listener: EventListener) : RecyclerView.Adapter<MovieViewHolder> () {

    private var items: ArrayList<Movies>? = ArrayList()
    private val eventListner=listener;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView= MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
        return itemView    }

    override fun getItemCount(): Int {
        if(items!=null) {
            return items!!.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.mName.text = items?.get(position)?.mName.toString()
    }

    fun setMovies(items: ArrayList<Movies>?){
        this.items = items
    }

    public interface EventListener {
        fun onSubmitSelected(position: Int)
        fun onEmployeeSelected(position: Int)
    }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mName = view.mName
    val movie_poster = view.movie_poster
}