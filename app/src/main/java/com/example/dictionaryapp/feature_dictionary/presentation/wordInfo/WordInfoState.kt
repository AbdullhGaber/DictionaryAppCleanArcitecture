package com.example.dictionaryapp.feature_dictionary.presentation.wordInfo

import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfos : List<WordInfo> = emptyList(),
    val isLoading : Boolean = false
)
