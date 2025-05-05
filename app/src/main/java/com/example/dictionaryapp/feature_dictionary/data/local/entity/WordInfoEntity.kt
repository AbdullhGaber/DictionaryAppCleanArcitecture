package com.example.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.feature_dictionary.data.remote.dto.LicenseDto
import com.example.dictionaryapp.feature_dictionary.data.remote.dto.MeaningDto
import com.example.dictionaryapp.feature_dictionary.data.remote.dto.PhoneticDto
import com.example.dictionaryapp.feature_dictionary.domain.model.License
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.example.dictionaryapp.feature_dictionary.domain.model.Phonetic
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey
    val id : Int = 0,
    val word : String,
    val phonetic : String,
    val license: License,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
){
    fun toWordInfo() : WordInfo{
        return WordInfo(license, phonetic, meanings, phonetics, sourceUrls, word)
    }
}