package com.my.dogapp.di.module

import android.content.Context
import com.my.dogapp.data.persistence.ApplicationDatabase
import com.my.dogapp.data.persistence.dao.BreedDao
import com.my.dogapp.data.persistence.dao.BreedPostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PersistenceModule {
    @Provides
    @Singleton
    fun getApplicationDatabase(
        @ApplicationContext context: Context,
    ): ApplicationDatabase = ApplicationDatabase.create(context)

    @Provides
    @Singleton
    fun providesBreedDao(applicationDatabase: ApplicationDatabase): BreedDao {
        return applicationDatabase.getBreedDao()
    }

    @Provides
    @Singleton
    fun providesBreedPostDao(applicationDatabase: ApplicationDatabase): BreedPostDao {
        return applicationDatabase.getBreedPostDao()
    }
}