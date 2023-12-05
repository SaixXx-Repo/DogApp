package com.my.dogapp.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breeds")
data class BreedEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "breed_name")
    var breedName: String,
    @ColumnInfo(name = "sub_breed_name")
    var subBreedName: String?
)
