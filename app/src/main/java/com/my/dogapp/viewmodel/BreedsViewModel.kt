package com.my.dogapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.my.dogapp.utils.api.ApiResponse
import com.my.dogapp.data.repository.DogRepository
import com.my.dogapp.viewholderfactory.BreedItemViewHolderFactory
import com.my.dogapp.model.BreedDto
import com.my.dogapp.utils.Event
import com.my.dogapp.utils.adapter.ClickType
import com.my.dogapp.utils.adapter.RecyclerListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel
@Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel(), RecyclerListItem.ClickListener {

    val breedViewHolderFactory = listOf(
        BreedItemViewHolderFactory(this)
    )

    val breeds = dogRepository.getBreedsFlow().asLiveData()

    fun getAllBreeds() {
        viewModelScope.launch(Dispatchers.IO) {
           dogRepository.fetchAllBreeds{ result ->
               when(result){
                   is ApiResponse.Loading -> {
                       println("Loading getAllBreeds")
                   }
                   is ApiResponse.Success -> {
                       val mapResponse = result.data.message

                       val subs: List<BreedDto>?  = mapResponse?.flatMap { (breedName, subNameList) ->
                           if (subNameList.isEmpty()) {
                               listOf(BreedDto(
                                   breedName = breedName,
                                   subBreedName = null
                               ))
                           } else {
                               subNameList.map {subName: String ->
                                   BreedDto(
                                       breedName = breedName,
                                       subBreedName = subName
                                   )
                               }
                           }
                       }

                       subs?.let {
                           dogRepository.saveBreeds(it)
                       }
                   }
                   is ApiResponse.Error -> {
                       println("Error getAllBreeds")
                   }
               }
           }
        }
    }

    private val _navigateToFragmentEvent = MutableLiveData<Event<BreedDto>>()
    val navigateToFragmentEvent: LiveData<Event<BreedDto>> get() = _navigateToFragmentEvent


    override fun onClickRecyclerItem(item: RecyclerListItem, clickType: ClickType) {
       if (item is BreedDto){
           _navigateToFragmentEvent.value = Event(item)
       }
    }
}