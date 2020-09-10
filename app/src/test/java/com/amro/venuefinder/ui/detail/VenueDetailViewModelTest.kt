package com.amro.venuefinder.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amro.venuefinder.TestCoroutineRule
import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.data.VenueDetailResponse
import com.amro.venuefinder.data.VenueDetailResult
import com.amro.venuefinder.repository.VenueRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

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
                .`when`(repository).details(detail_id, true)
            viewModel = VenueDetailViewModel(repository)
            viewModel.venue.observeForever(venue)
            viewModel.fetchVenueDetails(detail_id, true)
            Mockito.verify(repository).details(detail_id, true)
            Mockito.verify(venue).onChanged(Mockito.any())
            viewModel.venue.removeObserver(venue)
        }
    }

    @Test
    fun `search error`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).details(detail_id, true)
            viewModel = VenueDetailViewModel(repository)
            viewModel.isError.observeForever(isError)
            viewModel.fetchVenueDetails(detail_id, true)
            Mockito.verify(isError).onChanged(ArgumentMatchers.any())
            viewModel.isError.removeObserver(isError)
        }
    }

    companion object {
        const val detail_id = "acsdd"
    }

}