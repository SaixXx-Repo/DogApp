package com.my.dogapp.data.repository

import com.my.dogapp.data.persistence.dao.BreedDao
import com.my.dogapp.data.persistence.dao.BreedPostDao
import com.my.dogapp.data.persistence.entity.BreedEntity
import com.my.dogapp.data.persistence.entity.BreedPostEntity
import com.my.dogapp.data.remote.api.DogApi
import com.my.dogapp.data.remote.response.DogApiBaseResponse
import com.my.dogapp.model.BreedDto
import com.my.dogapp.model.BreedPostDto
import com.my.dogapp.model.extension.asBreedDto
import com.my.dogapp.model.extension.asBreedEntity
import com.my.dogapp.model.extension.asBreedPostDto
import com.my.dogapp.model.extension.asBreedPostEntity
import com.my.dogapp.utils.api.ApiResponse
import com.my.dogapp.utils.api.handleApiCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val api: DogApi,
    private val breedDao: BreedDao,
    private val breedPostDao: BreedPostDao
) {

    fun getBreedsFlow(): Flow<List<BreedDto>> {
        return breedDao.getAllBreedFlow().map { it.map(BreedEntity::asBreedDto) }
    }

    fun getBreedPostsFlow(): Flow<List<BreedPostDto>> {
        return breedPostDao.getAllBreedPostsFlow().map { it.map(BreedPostEntity::asBreedPostDto) }
    }

    fun getFavouritePostsFlow(): Flow<List<BreedPostDto>> {
        return breedPostDao.getFavouritePostsFlow().map { it.map(BreedPostEntity::asBreedPostDto) }
    }

    fun addOrRemoveFavourite(url: String) {
        breedPostDao.addOrRemoveFavourite(url)
    }

    fun fetchAllBreeds(onResult: (ApiResponse<DogApiBaseResponse<Map<String, List<String>>>>) -> Unit) {
        handleApiCall(onResult, api.getAllBreeds())
    }

    fun fetchBreedImages(
        breed: BreedDto,
        onResult: (ApiResponse<DogApiBaseResponse<List<String>>>) -> Unit
    ) {
        if (breed.subBreedName.isNullOrBlank()) {
            handleApiCall(onResult, api.getBreedImages(breed.breedName))
        } else {
            handleApiCall(onResult, api.getSubBreedImages(breed.breedName, breed.subBreedName))
        }
    }

    fun saveBreeds(breedList: List<BreedDto>) = CoroutineScope(Dispatchers.IO).launch {
        breedDao.insertAllBreeds(breedList.map(BreedDto::asBreedEntity))
    }

    fun savePostsForBreed(breedList: List<BreedPostDto>) = CoroutineScope(Dispatchers.IO).launch {
        breedPostDao.insertPosts(breedList.map(BreedPostDto::asBreedPostEntity))
    }
}