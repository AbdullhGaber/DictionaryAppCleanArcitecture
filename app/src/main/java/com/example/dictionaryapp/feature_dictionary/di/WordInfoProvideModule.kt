package com.example.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.example.dictionaryapp.feature_dictionary.data.local.Converters
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.data.util.Constants.BASE_URL
import com.example.dictionaryapp.feature_dictionary.data.util.GsonParser
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dictionary.domain.usecases.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoProvideModule {
    @Provides
    @Singleton
    fun provideWordInfoRoomDB(
        app : Application
    ) : WordInfoDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = WordInfoDatabase::class.java,
            name = "wordInfo_db"
        ).addTypeConverter(Converters(GsonParser(Gson()))).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideGetWordInfo(wordInfoRepository : WordInfoRepository) : GetWordInfo {
        return GetWordInfo(wordInfoRepository)
    }

    @Provides
    @Singleton
    fun provideGetWordDao(wordInfoDatabase:WordInfoDatabase) : WordInfoDao {
        return wordInfoDatabase.wordInfoDao
    }

    @Provides
    @Singleton
    fun provideRetrofitHelper() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWordInfoService(retrofit : Retrofit) : DictionaryApi {
        return retrofit.create(DictionaryApi::class.java)
    }
}