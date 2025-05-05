package com.example.dictionaryapp.feature_dictionary.data.remote.dto

import com.example.dictionaryapp.feature_dictionary.domain.model.License
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning

data class MeaningDto(
    val antonyms: List<String?>? = null,
    val definitions: List<DefinitionDto?>? = null,
    val partOfSpeech: String? = null,
    val synonyms: List<String?>? = null
){
    fun toMeaning() : Meaning {
        return Meaning(antonyms, definitions?.map{it?.toDefinition()}, partOfSpeech, synonyms)
    }
}