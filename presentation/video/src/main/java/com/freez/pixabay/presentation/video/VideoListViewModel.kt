package com.freez.pixabay.presentation.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(private val searchLongVideoUseCase: SearchLongVideoListUseCase) :
    ViewModel() {

    private var _videoPosts = MutableStateFlow<List<VideoPost>>(emptyList())
    val videoPost: StateFlow<List<VideoPost>> get() = _videoPosts

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> get() = _loading

    private fun loadCacheVideos() {
    }

    private fun searchVideoPost(searchKey: String) {
        viewModelScope.launch {
            try {
                searchLongVideoUseCase.execute(searchKey)
                    .collect { videoPostList ->
                        _videoPosts.value = videoPostList.toMutableList()
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }

    }

    private var searchJob: Job? = null

    // Function to handle search input
    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()

        // Debounce API call
        searchJob = viewModelScope.launch {
            delay(500L)
            if (query.isNotEmpty()) {
                // Call the API and update the search results state
                performApiSearch(query)
            }
        }
    }

    private suspend fun performApiSearch(query: String) {
        if (query.isEmpty())
            return
        delay(1000L)
        searchVideoPost(query)
    }

    fun changeBookmark(bookmark: Boolean) {
    }
}