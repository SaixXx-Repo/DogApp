package com.my.dogapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.my.dogapp.data.repository.DogRepository
import com.my.dogapp.viewholderfactory.BreedPostItemViewHolderFactory
import com.my.dogapp.model.BreedPostDto
import com.my.dogapp.model.Filter
import com.my.dogapp.utils.adapter.ClickType
import com.my.dogapp.utils.adapter.RecyclerListItem
import com.my.dogapp.viewholderfactory.FilterItemViewHolderFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel
@Inject constructor(
    private val dogRepository: DogRepository
) : ViewModel(), RecyclerListItem.ClickListener {

    val breedPostViewHolderFactory = listOf(BreedPostItemViewHolderFactory(this))
    val filterViewHolderFactory = listOf(FilterItemViewHolderFactory(this))

    private val appliedFilters: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    val filters = dogRepository.getFavouritePostsFlow()
        .combineTransform(appliedFilters) { posts, filterList ->
            if (posts.isEmpty()) {
                emit(emptyList())
                return@combineTransform
            }
            val allFilter = listOf(Filter(ALL_FILTER, filterList.contains(ALL_FILTER)))
            val breedFilters = posts.map {
                Filter(
                    name = it.getFullName(),
                    isApplied = filterList.contains(it.getFullName())
                )
            }.distinct()
            emit(allFilter.plus(breedFilters))
        }.asLiveData()


    val favouritePosts =
        dogRepository.getFavouritePostsFlow().combine(appliedFilters) { posts, filters ->
            if (filters.isEmpty()) {
                posts
            } else {
                val allPossibleFilters = posts.map { it.getFullName() }
                val notExistingFilters = filters.subtract(allPossibleFilters.toSet())
                if (notExistingFilters.isNotEmpty()) {
                    appliedFilters.getAndUpdate {
                        it.minus(notExistingFilters)
                    }
                }
                posts.filter { filters.contains(it.getFullName()) }
            }
        }.asLiveData()

    private fun handleFilterAction(filter: Filter) {
        appliedFilters.getAndUpdate {
            if (filter.name == ALL_FILTER) {
                emptyList()
            } else {
                if (it.contains(filter.name)) {
                    it.minus(filter.name)
                } else {
                    it.plus(filter.name)
                }
            }
        }
    }

    override fun onClickRecyclerItem(item: RecyclerListItem, clickType: ClickType) {
        when (item) {
            is BreedPostDto -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dogRepository.addOrRemoveFavourite(item.url)
                }
            }

            is Filter -> handleFilterAction(item)
        }
    }

    companion object {
        const val ALL_FILTER = "All"
    }
}