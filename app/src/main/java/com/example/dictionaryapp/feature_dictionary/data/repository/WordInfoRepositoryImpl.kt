package com.example.dictionaryapp.feature_dictionary.data.repository

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class WordInfoRepositoryImpl @Inject constructor(
    private val mApi : DictionaryApi,
    private val mDao : WordInfoDao
) : WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        try {
            val wordInfosResponse = mApi.getWordDef(word)
            mDao.deleteWordInfos(wordInfosResponse.map{it.word})
            mDao.insertWordInfos(wordInfosResponse.map{it.toWordInfoEntity()})
        }catch (e : HttpException){
            emit(Resource.Failure(
                message = "Something went wrong! : ${e.localizedMessage}"
            ))
        }catch (e : IOException){
            emit(Resource.Failure(
                message = "Something went wrong! : ${e.localizedMessage}"
            ))
        }

        val wordInfos = mDao.getWordInfos(word).map { it.toWordInfo() }

        emit(Resource.Success(data = wordInfos))
    }
}