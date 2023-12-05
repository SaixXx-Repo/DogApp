package com.my.dogapp.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed_posts")
data class BreedPostEntity (
    @PrimaryKey
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "breed_name")
    var breedName: String,
    @ColumnInfo(name = "sub_breed_name")
    var subBreedName: String?,
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean,
)
