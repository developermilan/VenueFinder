package com.amro.venuefinder.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.util.lruCache
import com.amro.venuefinder.TestCoroutineRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
            repository = VenueRepository(true, remoteDataSource, localDataSource)
            repository.search("Pune")
            Mockito.verify(remoteDataSource).search("Pune")
        }
    }

    @Test
    fun `local search call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(false, remoteDataSource, localDataSource)
            repository.search("Pune")
            Mockito.verify(localDataSource).search("Pune")
        }
    }

    @Test
    fun `remote details call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(true, remoteDataSource, localDataSource)
            repository.details("abscc")
            Mockito.verify(remoteDataSource).details("abscc")
        }
    }

    @Test
    fun `local details call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(false, remoteDataSource, localDataSource)
            repository.details("abscc")
            Mockito.verify(localDataSource).details("abscc")
        }
    }

    @Test
    fun `deleteAll call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(true, remoteDataSource, localDataSource)
            repository.deleteAll()
            Mockito.verify(localDataSource).deleteAll()
        }
    }

    @Test
    fun `deleteAll call failure`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(false, remoteDataSource, localDataSource)
            repository.deleteAll()
            Mockito.verifyNoInteractions(localDataSource)
        }
    }

    @Test
    fun `insert venues call`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(true, remoteDataSource, localDataSource)
            repository.insertVenues(emptyList())
            Mockito.verify(localDataSource).insert(Mockito.anyList())
        }
    }

    @Test
    fun `insert venues call failure`() {
        testCoroutineRule.runBlockingTest {
            repository = VenueRepository(false, remoteDataSource, localDataSource)
            repository.insertVenues(emptyList())
            Mockito.verifyNoInteractions(localDataSource)
        }
    }
}