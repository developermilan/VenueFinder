package com.amro.venuefinder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.sql.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }

    override fun apply(base: org.junit.runners.model.Statement?, description: Description?) =
        object : org.junit.runners.model.Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)

                base?.evaluate()

                Dispatchers.resetMain()
                testCoroutineScope.cleanupTestCoroutines()
            }
        }

}