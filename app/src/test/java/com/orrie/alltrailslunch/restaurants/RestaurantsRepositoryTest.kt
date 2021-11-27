package com.orrie.alltrailslunch.restaurants

import com.orrie.alltrailslunch.BaseUnitTest
import com.orrie.alltrailslunch.restaurants.services.RestaurantsService
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RestaurantsRepositoryTest : BaseUnitTest() {

    lateinit var sut: RestaurantsRepository
    private val mockRestaurantsService = mock<RestaurantsService> {}
    private val mockRestaurantsStore = mock<RestaurantsStore> {}

    // Constants
    private val heartedRestaurant1 = "hearted1"
    private val heartedRestaurant2 = "hearted2"
    private val unheartedRestaurant1 = "unhearted1"
    private val setHearted = true
    private val setUnhearted = false

    @Before
    override fun setUp() {
        super.setUp()

        loadKoinModules(
            module {
                single { mockRestaurantsService }
                single { mockRestaurantsStore }
            }
        )

        whenever(mockRestaurantsStore.fetchHeartedRestaurantIds()).thenReturn(setOf(heartedRestaurant1, heartedRestaurant2))

        sut = RestaurantsRepository()
    }

    /**
     * Note to reviewer: Each test is split up into the Given (sets up the test
     * scenario), When (performs the test action) and Then (asserts the results
     * of the action) sections. I've commented this explicity in the first test
     * however normally these secctions are just denoted by a blank line between
     * sections.
     */
    @Test
    fun `test hearted restaurant is in repository`() {
        // Given

        // In this case we already initialized the scenario in the setup when we
        // set the mock store to return a set of hearted restaurant ids, so nothing
        // goes here

        // When
        // Note to reviewer: One of the most important parts of testing is that
        // everything is really easy to read. Tests act as a form of documentation
        // with that the benefit that they are always up to date, so it's really
        // important any dev can read the test easily and understand the behavior.
        // That's why the constants are defined at the top, because their actual
        // value isn't important to understanding what is happening in the test.
        val result = sut.isRestaurantHearted(heartedRestaurant1)

        // Then
        assertEquals(true, result)
    }

    @Test
    fun `test restaurant that has not been hearted is not in the repository`() {
        val result = sut.isRestaurantHearted(unheartedRestaurant1)

        assertEquals(false, result)
    }

    @Test
    fun `test restaurant is hearted that has not already been hearted`() {
        // Note to reviewer: Instead of using 'true' for the second parameter, use
        // constants to keep the test readable. Otherwise reviewer would have to look
        // at the function parameters to understand what the second parameter is
        sut.setRestaurantHeartedState(unheartedRestaurant1, setHearted)
        val result = sut.isRestaurantHearted(unheartedRestaurant1)

        verify(mockRestaurantsStore).updateHeartedRestaurantIds(
            setOf(heartedRestaurant1, heartedRestaurant2, unheartedRestaurant1)
        )
        assertEquals(true, result)
    }

    @Test
    fun `test restaurant is hearted that has already been hearted`() {
        sut.setRestaurantHeartedState(heartedRestaurant1, setHearted)
        val result = sut.isRestaurantHearted(heartedRestaurant1)

        verify(mockRestaurantsStore).updateHeartedRestaurantIds(
            setOf(heartedRestaurant1, heartedRestaurant2)
        )
        assertEquals(true, result)
    }

    @Test
    fun `test restaurant is unhearted that has already been hearted`() {
        sut.setRestaurantHeartedState(heartedRestaurant1, setUnhearted)
        val result = sut.isRestaurantHearted(heartedRestaurant1)

        verify(mockRestaurantsStore).updateHeartedRestaurantIds(
            setOf(heartedRestaurant2)
        )
        assertEquals(false, result)
    }

    @Test
    fun `test restaurant is unhearted that has not already been hearted`() {
        sut.setRestaurantHeartedState(unheartedRestaurant1, setUnhearted)
        val result = sut.isRestaurantHearted(unheartedRestaurant1)

        verify(mockRestaurantsStore).updateHeartedRestaurantIds(
            setOf(heartedRestaurant1, heartedRestaurant2)
        )
        assertEquals(false, result)
    }
}