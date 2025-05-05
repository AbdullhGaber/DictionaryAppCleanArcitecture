package com.example.dictionaryapp.feature_dictionary.presentation.wordInfo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.usecases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val mGetWord : GetWordInfo
) : ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery : State<String> = _searchQuery

    private val _wordInfo = mutableStateOf(WordInfoState())
    val wordInfo : State<WordInfoState> = _wordInfo

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob : Job? = null

    fun onSearch(word : String){
        _searchQuery.value = word
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000)
            mGetWord(word)
                .onEach { result ->
                    when(result){
                        is Resource.Failure -> {
                            _wordInfo.value = _wordInfo.value.copy(
                                wordInfos = result.data?: emptyList(),
                                isLoading = false
                            )

                            _eventFlow.emit(UIEvent.ShowSnackBar(result.message!!))
                        }
                        is Resource.Loading -> {
                            _wordInfo.value = _wordInfo.value.copy(
                                wordInfos = result.data?: emptyList(),
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _wordInfo.value = _wordInfo.value.copy(
                                wordInfos = result.data?: emptyList(),
                                isLoading = false
                            )
                        }

                        is Resource.Unspecified -> Unit
                    }
                }.launchIn(viewModelScope)
        }
    }
    sealed class UIEvent{
        data class ShowSnackBar(val message : String) : UIEvent()
    }
}