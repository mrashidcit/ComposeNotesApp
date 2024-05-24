package com.rashidsaleem.notesapp.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _scope = viewModelScope
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun action(action: HomeAction) {
        when (action) {
            HomeAction.AddNewNote -> addNewNote()
            is HomeAction.ItemOnClick -> itemOnClick(action.value)
        }
    }

    private fun itemOnClick(value: Note) {

    }

    private fun addNewNote() = _scope.launch {

    }

}