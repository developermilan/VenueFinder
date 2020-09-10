package com.amro.venuefinder.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amro.venuefinder.TestCoroutineRule
import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.data.VenueSearchResponse
import com.amro.venuefinder.data.VenueSearchResult
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
class VenueListViewModelTest {

    @get:Rule
    var instantTaskExecuterRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: VenueRepository

    @Mock
    private lateinit var venueList: Observer<List<Venue>>

    @Mock
    private lateinit var isLoading: Observer<Boolean>

    @Mock
    private lateinit var isError: Observer<Throwable>

    @Mock
    private lateinit var clickedPosition: Observer<Int>

    private lateinit var viewModel: VenueListViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `search success`() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(VenueSearchResult(VenueSearchResponse(emptyList<Venue>())))
                .`when`(repository).search(search_val, true)
            viewModel = VenueListViewModel(repository)
            viewModel.venues.observeForever(venueList)
            viewModel.isLoading.observeForever(isLoading)
            viewModel.search(search_val, true)
            Mockito.verify(isLoading).onChanged(true)
            Mockito.verify(repository).search(search_val, true)
            Mockito.verify(venueList).onChanged(Mockito.any())
            Mockito.verify(repository).deleteAll(true)
            Mockito.verify(repository).insertVenues(Mockito.anyList(), Mockito.eq(true))
            Mockito.verify(isLoading).onChanged(false)
            viewModel.venues.removeObserver(venueList)
            viewModel.isLoading.removeObserver(isLoading)
        }
    }

    @Test
    fun `search error`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository).search(search_val, true)
            viewModel = VenueListViewModel(repository)
            viewModel.isLoading.observeForever(isLoading)
            viewModel.isError.observeForever(isError)
            viewModel.search(search_val, true)
            Mockito.verify(isLoading).onChanged(false)
            Mockito.verify(isError).onChanged(ArgumentMatchers.any())
            viewModel.isLoading.removeObserver(isLoading)
            viewModel.isError.removeObserver(isError)
        }
    }

    @Test
    fun `item clicked`() {
        viewModel = VenueListViewModel(repository)
        viewModel.clickedItemPosition.observeForever(clickedPosition)
        viewModel.itemClicked(0)
        Mockito.verify(clickedPosition).onChanged(0)
    }

    companion object {
        const val search_val = "Pune"
    }
}