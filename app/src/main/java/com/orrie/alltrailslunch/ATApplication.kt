package com.orrie.alltrailslunch

import android.app.Application
import android.content.Context
import com.orrie.alltrailslunch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * --PROJECT STRUCTURE
 *
 * I like to structure my project by function or domain, rather than by type. For example instead of having:
 * -views
 *   -RestaurantListFragment
 * -viewModels
 *   -RestaurantsListViewModel
 * -repositories
 *   -RestaurantsRepository
 *
 *  instead have:
 *  -restaurants
 *    -RestaurantRepository
 *    -search
 *      -list
 *        -RestaurantListFragment
 *        -RestaurantsListViewModel
 *
 * It seems more intuitive to structure by type, however in practice I have found it's never very useful. It makes
 * finding the file you're looking for a lot easier because you can drill down based on domain.
 * and you are far more likely to want to see files related to a particular file rather than other files of the
 * same type.
 *
 * --DOCUMENTATION
 *
 * You'll notice that there is fairly limited documentation throughout the code.
 * I'm a big believer that your code should be self documenting and documentation should only be
 * added where it can't be made obvious from the code what is happening. Two exceptions to this are:
 *
 * 1. I like to add documentation to the top of view and view model classes. This is so if someone lands on a
 * file for the first time, they can get an idea of what screen it is used in without having to read
 * through the implementation details or navigate to the layout file.
 *
 * 2. I've added several "Note to reviewer:" comments. This is just for things that might be different than from
 * what you, the reviewer, have seen before. These comments wouldn't normally be included because I would
 * have discussed these approaches with the rest of the android developers to make sure everyone is okay
 * with it and are on the same page.
 */
class ATApplication : Application() {

    companion object {
        // Note to reviewer: It is debatable whether or not making application context publicly
        // accessible is a good idea or not. I have found that the benefits it provides greatly
        // outweigh any negatives as long as it is not abused. Happy to discuss more in a review.
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this

        // Note to reviewer: I have used Dagger as well but prefer Koin for simple projects
        startKoin{
            androidLogger()
            androidContext(this@ATApplication)
            modules(appModule)
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}