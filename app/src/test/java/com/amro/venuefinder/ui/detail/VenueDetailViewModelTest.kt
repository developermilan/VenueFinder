package com.amro.venuefinder.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amro.venuefinder.TestCoroutineRule
import com.amro.venuefinder.data.*
import com.amro.venuefinder.repository.VenueRepository
import com.amro.venuefinder.ui.list.VenueListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class VenueDetailViewModelTest {

    @get:Rule
    var instantTaskExecuterRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: VenueRepository

    @Mock
    private lateinit var isError: Observer<Throwable>

    @Mock
    private lateinit var venue: Observer<Venue>

    private lateinit var viewModel: VenueDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `detail success`() {
        testCoroutineRule.runBlockingTest {
            val venueObj = Venue("null", null, null, null, null)
            Mockito.doReturn(VenueDetailResult(VenueDetailResponse(venueObj)))
                .`when`(repository).details("acsdd")
            viewModel = VenueDetailViewModel(repository)
            viewModel.venue.observeForever(venue)
            viewModel.fetchVenueDetails("acsdd")
            Mockito.verify(repository).details("acsdd")
            Mockito.verify(venue).onChanged(Mockito.any())
            viewModel.venue.removeObserver(venue)
        }
    }

    @Test
    fun `search error`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).details("acsdd")
            viewModel = VenueDetailViewModel(repository)
            viewModel.isError.observeForever(isError)
            viewModel.fetchVenueDetails("acsdd")
            Mockito.verify(isError).onChanged(ArgumentMatchers.any())
            viewModel.isError.removeObserver(isError)
        }
    }

}