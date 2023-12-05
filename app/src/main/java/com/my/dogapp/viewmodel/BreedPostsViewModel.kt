package com.my.dogapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.my.dogapp.utils.api.ApiResponse
import com.my.dogapp.data.repository.DogRepository
import com.my.dogapp.viewholderfactory.BreedPostItemViewHolderFactory
import com.my.dogapp.model.BreedDto
import com.my.dogapp.model.BreedPostDto
import com.my.dogapp.utils.adapter.ClickType
import com.my.dogapp.utils.adapter.RecyclerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedPostsViewModel
@Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel(), RecyclerListItem.ClickListener {

    private val TAG = "BreedPostsViewModel"
    val isLoading = MutableStateFlow(false)

    val breedPostViewHolderFactory = listOf(BreedPostItemViewHolderFactory(this))

    val selectedBreed: MutableStateFlow<BreedDto?> = MutableStateFlow(null)

    val breedPosts = dogRepository.getBreedPostsFlow().map {list ->
        list.filter {post ->
            post.breedName == selectedBreed.value?.breedName &&
                    post.subBreedName == selectedBreed.value?.subBreedName
        }
    }.asLiveData()

    fun fetchBreedImages() {
        viewModelScope.launch(Dispatchers.IO) {
            val dto = selectedBreed.value ?: return@launch
            dogRepository.fetchBreedImages(dto) { result ->
                when (result) {
                    is ApiResponse.Loading -> isLoading.value = true
                    is ApiResponse.Success -> {
                        val urlList = result.data.message
                        val postList = urlList?.map{ url ->
                            BreedPostDto(
                                breedName = dto.breedName,
                                subBreedName = dto.subBreedName,
                                url = url,
                                isFavourite = false
                            )
                        }
                        postList?.let {
                            dogRepository.savePostsForBreed(it)
                        }
                        isLoading.value = false
                    }
                    is ApiResponse.Error -> {
                        isLoading.value = false
                        Log.e(TAG,"Error fetch breed images")
                    }
                }
            }
        }
    }

    override fun onClickRecyclerItem(item: RecyclerListItem, clickType: ClickType) {
        if (item is BreedPostDto) {
            viewModelScope.launch(Dispatchers.IO) {
                dogRepository.addOrRemoveFavourite(item.url)
            }
        }
    }
}