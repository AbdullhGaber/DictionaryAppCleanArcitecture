package com.example.dictionaryapp.feature_dictionary.domain.model

import com.example.dictionaryapp.feature_dictionary.data.remote.dto.DefinitionDto

data class Meaning(
    val antonyms: List<String?>? = null,
    val definitions: List<Definition?>? = null,
    val partOfSpeech: String? = null,
    val synonyms: List<String?>? = null
)

