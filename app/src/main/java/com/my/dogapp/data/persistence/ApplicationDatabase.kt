package com.my.dogapp.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.my.dogapp.data.persistence.ApplicationDatabase.Companion.VERSION
import com.my.dogapp.data.persistence.dao.BreedDao
import com.my.dogapp.data.persistence.dao.BreedPostDao
import com.my.dogapp.data.persistence.entity.BreedEntity
import com.my.dogapp.data.persistence.entity.BreedPostEntity

@Database(
    version = VERSION,
    entities = [
        BreedEntity::class,
        BreedPostEntity::class
    ]
)

abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getBreedDao(): BreedDao
    abstract fun getBreedPostDao(): BreedPostDao

    companion object {
        private const val NAME = "ApplicationDatabase.db"
        const val VERSION = 1

        fun create(
            context: Context
        ): ApplicationDatabase {
            return Room.databaseBuilder(
                context,
                ApplicationDatabase::class.java,
                NAME
            ).build()
        }
    }
}