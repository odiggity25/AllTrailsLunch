package com.orrie.alltrailslunch

import com.orrie.alltrailslunch.di.appModule
import junit.framework.TestCase
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

open class BaseUnitTest : TestCase(), KoinTest {

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appModule)
        }
    }

    override fun tearDown() {
        super.tearDown()
        stopKoin()
    }
}