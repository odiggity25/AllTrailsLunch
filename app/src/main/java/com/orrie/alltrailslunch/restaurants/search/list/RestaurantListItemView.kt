package com.orrie.alltrailslunch.restaurants.search.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.orrie.alltrailslunch.R
import com.orrie.alltrailslunch.databinding.RestaurantListItemViewBinding
import com.orrie.alltrailslunch.restaurants.models.Restaurant
import com.orrie.alltrailslunch.shared.resourceColor
import com.orrie.alltrailslunch.shared.resourceString
import com.orrie.alltrailslunch.shared.views.dpToPx
import com.orrie.alltrailslunch.shared.views.pxToDp
import com.orrie.alltrailslunch.shared.views.setVisibility
import com.orrie.alltrailslunch.shared.views.subscribeToViewModelObservable
import com.orrie.alltrailslunch.shared.views.throttleTaps
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt

/**
 * This is a restaurant search result card in the recyclerview and contains info
 * including name, image, price and rating
 */
class RestaurantListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    val forMapInfoWindow: Boolean = false
) : FrameLayout(context, attrs, defStyle), KoinComponent {

    private val viewModel: RestaurantListItemViewModel by inject()
    // Note to reviewer: Ide probably says this can be joined with assignment however I prefer
    // to keep them separate to keep things consistent with activities and fragments
    private val binding: RestaurantListItemViewBinding

    init {
        binding = RestaurantListItemViewBinding.inflate(LayoutInflater.from(context), this, true)
        layoutParams = if (forMapInfoWindow) {
            LayoutParams(300.pxToDp(), 100.pxToDp())
        } else {
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }
        initUi()
        subscribeToViewModelObservables()
    }

    private fun initUi() {
        binding.heartButton.throttleTaps { viewModel.heartTapped() }
    }

    private fun subscribeToViewModelObservables() {
        viewModel.heartChanged.subscribeToViewModelObservable {
            binding.heartButton.setImageResource(if (it) R.drawable.ic_heart_filled else R.drawable.ic_heart_unfilled)
        }
    }

    fun bind(restaurant: Restaurant) {
        viewModel.bind(restaurant)
        updateImage(restaurant)
        binding.restaurantName.text = restaurant.name
        updateRatingSection(restaurant)
        updateCostSection(restaurant)
        binding.heartButton.setVisibility(!forMapInfoWindow)
    }

    private fun updateImage(restaurant: Restaurant) {
        restaurant.photoUrl?.let {
            Glide.with(context).load(it).into(binding.restaurantImage)
        } ?: run {
            // Make sure to clear image so if view is re-used with a restaurant that doesn't have an
            // image, the old restaurant image isn't displayed
            binding.restaurantImage.setImageDrawable(null)
        }
    }

    private fun updateCostSection(restaurant: Restaurant) {
        var dollarSigns = ""
        repeat(restaurant.priceLevel ?: 0) { dollarSigns += "$" }
        val costMoreInfoString = R.string.costAndMoreInfo.resourceString(dollarSigns, restaurant.supportingText ?: "")
        binding.costAndMoreInfo.text = costMoreInfoString
    }

    @Suppress("DEPRECATION")
    private fun updateRatingSection(restaurant: Restaurant) {
        binding.ratingContainer.removeAllViews()
        binding.ratingContainer.setVisibility(restaurant.rating != null)
        val rating = restaurant.rating ?: return
        binding.ratingContainer.apply {
            // Note to reviewer: Going to just round for speed. Would do half stars in real life
            val stars = rating.roundToInt()
            repeat(stars) { addView(makeStarView(true)) }
            repeat(5 - stars) { addView(makeStarView(false)) }
            addView(TextView(context).apply {
                layoutParams =
                    LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                        setMargins(8.pxToDp(), 0, 0, 0)
                    }
                setTextAppearance(context, R.style.subheading1)
                text = R.string.num_reviews.resourceString(restaurant.numReviews ?: 0)
            })
        }
    }

    private fun makeStarView(filled: Boolean): ImageView {
        return ImageView(context).apply {
            val dimension = 24.dpToPx()
            setBackgroundResource(R.drawable.ic_star_filled)
            if (!filled) background.setTint(R.color.grey_dark.resourceColor())
            layoutParams = LayoutParams(dimension, dimension)
        }
    }
}