package com.example.dictionaryapp.feature_dictionary.domain.usecases

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordInfo @Inject constructor(
    private val mWordInfoRepository: WordInfoRepository
) {
    operator fun invoke(word : String) : Flow<Resource<List<WordInfo>>>{
        return mWordInfoRepository.getWordInfo(word)
    }
}