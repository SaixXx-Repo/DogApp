package com.my.dogapp.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.my.dogapp.data.persistence.entity.BreedPostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedPostDao {

    @Query("SELECT * FROM breed_posts")
    fun getAllBreedPostsFlow(): Flow<List<BreedPostEntity>>

    @Query("SELECT * FROM breed_posts WHERE is_favourite = 1")
    fun getFavouritePostsFlow(): Flow<List<BreedPostEntity>>

    @Query("UPDATE breed_posts SET is_favourite = NOT is_favourite WHERE url == :url")
    fun addOrRemoveFavourite(url: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPosts(breedData: List<BreedPostEntity>)
}