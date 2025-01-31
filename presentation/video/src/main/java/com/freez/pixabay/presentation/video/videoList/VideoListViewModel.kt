package com.freez.pixabay.presentation.video.videoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.pixabay.domain.videodomain.BookmarkUseCase
import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val searchLongVideoUseCase: SearchLongVideoListUseCase,
    private val bookmarkUseCase: BookmarkUseCase,

    ) : ViewModel() {

    private var _videoPosts = MutableStateFlow<List<VideoPost>>(emptyList())
    val videoPost: StateFlow<List<VideoPost>> get() = _videoPosts

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading


    private var searchQuery: String? = null
    private var currentPage = 1
    private var isLastPage = false

    private fun searchVideoPost(searchKey: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            try {
                searchLongVideoUseCase.execute(searchKey, currentPage)
                    .collect { newVideoList ->
                        if (newVideoList.isEmpty()) {
                            isLastPage = true
                        } else {
                            val currentList = _videoPosts.value.toMutableList()

                            newVideoList.forEach { newCat ->
                                val existingCatIndex =
                                    currentList.indexOfFirst { it.id == newCat.id }
                                if (existingCatIndex != -1) { // update exist data
                                    currentList[existingCatIndex] = newCat
                                } else {
                                    currentList.add(newCat) //add new data
                                }
                            }
                            _videoPosts.value = currentList
                            currentPage++
                        }
                        _loading.value = false
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _loading.value = false
            }
        }
    }

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        if (searchQuery == query)
            return
        searchJob?.cancel()
        searchQuery = query
        currentPage = 1
        isLastPage = false

        searchJob = viewModelScope.launch {
            delay(500L)
            if (query.isNotEmpty()) {
                // Call the API and update the search results state
                performApiSearch(query)
            }
        }
    }

    private suspend fun performApiSearch(query: String) {
        if (query.isEmpty()) {
            return
        }
        delay(1000L)
        searchVideoPost(query)
    }

    fun changeBookmark(videId: Long, bookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkUseCase.changeBookmark(videId, bookmark)
        }
    }

    fun loadMoreData() {
        if (!isLastPage) {
            searchVideoPost(searchQuery ?: "")
        }
    }
}
