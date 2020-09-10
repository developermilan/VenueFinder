package com.amro.venuefinder.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amro.venuefinder.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class VenueRepositoryTest {

    @get:Rule
    var instantTaskExecuterRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var remoteDataSource: VenueRemoteDataSource

    @Mock
    private lateinit var localDataSource: VenueLocalDataSource

    private lateinit var repository: VenueRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `remote search call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.search(search_val, true)
            Mockito.verify(remoteDataSource).search(search_val)
        }
    }

    @Test
    fun `local search call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.search(search_val, false)
            Mockito.verify(localDataSource).search(search_val)
        }
    }

    @Test
    fun `remote details call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.details(details_id, true)
            Mockito.verify(remoteDataSource).details(details_id)
        }
    }

    @Test
    fun `local details call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.details(details_id, false)
            Mockito.verify(localDataSource).details(details_id)
        }
    }

    @Test
    fun `deleteAll call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.deleteAll(true)
            Mockito.verify(localDataSource).deleteAll()
        }
    }

    @Test
    fun `deleteAll call failure`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.deleteAll(false)
            Mockito.verifyNoInteractions(localDataSource)
        }
    }

    @Test
    fun `insert venues call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.insertVenues(emptyList(), true)
            Mockito.verify(localDataSource).insert(Mockito.anyList())
        }
    }

    @Test
    fun `insert venues call failure`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(remoteDataSource, localDataSource)
            repository.insertVenues(emptyList(), false)
            Mockito.verifyNoInteractions(localDataSource)
        }
    }

    companion object {
        const val search_val = "pune"
        const val details_id = "abscc"
    }
}