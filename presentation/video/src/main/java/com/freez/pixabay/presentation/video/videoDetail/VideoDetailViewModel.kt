package com.freez.pixabay.presentation.video.videoDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freez.pixabay.domain.videodomain.BookmarkUseCase
import com.freez.pixabay.domain.videodomain.VideoDetailUseCase
import com.freez.pixabay.domain.videodomain.entities.VideoPost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val videDetailUseCase: VideoDetailUseCase,
    private val bookmarkUseCase: BookmarkUseCase,

    ) : ViewModel() {

    private var _videoPosts = MutableStateFlow<VideoPost?>(null)
    val videoPost: StateFlow<VideoPost?> get() = _videoPosts

    private var _videoId = MutableStateFlow<Long?>(null)
    val videoId: StateFlow<Long?> get() = _videoId

    private var _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading


    private fun getVideoDetail(videoId: Long) {
        _loading.value = true
        viewModelScope.launch {
            try {
                videDetailUseCase.execute(videoId)
                    .collect { videoPostList ->
                        _videoPosts.value = videoPostList
                        _loading.value = false
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _loading.value = false
            }
        }
    }


    fun changeBookmark(videId: Long, bookmark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkUseCase.changeBookmark(videId, bookmark)
        }
    }

    fun setVideoId(id: Long) {
        _videoId.value = id
        videoId.value?.let { getVideoDetail(it) }
    }
}
