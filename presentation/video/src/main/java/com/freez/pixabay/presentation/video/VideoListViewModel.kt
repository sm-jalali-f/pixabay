package com.freez.pixabay.presentation.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.pixabay.domain.videodomain.SearchLongVideoListUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        searchVideoPost("flower")
    }

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
}