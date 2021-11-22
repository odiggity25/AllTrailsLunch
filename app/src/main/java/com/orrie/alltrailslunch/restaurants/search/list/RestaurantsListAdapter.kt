package com.orrie.alltrailslunch.restaurants.search.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orrie.alltrailslunch.restaurants.models.Restaurant

/**
 * Adapter for displaying restaurant search results in a list
 */
class RestaurantsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var restaurants: List<Restaurant> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(parent.context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RestaurantViewHolder -> (holder.itemView as RestaurantListItemView).bind(restaurants[position])
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    class RestaurantViewHolder(context: Context) : RecyclerView.ViewHolder(RestaurantListItemView(context))
}