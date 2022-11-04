package com.omaradev.androidtesting.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.use_case.NoteUseCases
import com.omaradev.androidtesting.presentation.add_edit_note.AddEditNoteViewModel
import com.omaradev.androidtesting.util.NoteOrder
import com.omaradev.androidtesting.util.OrderType
import com.omaradev.androidtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                getNotes(event.noteOrder)
            }
            is NotesEvent.DeleteNote -> {
                noteUseCases.deleteNote(event.note).onEach { response ->
                    when (response) {
                        is Resource.Success -> {
                            recentlyDeletedNote = event.note
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        noteUseCases.getNotes(noteOrder).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    _state.value =
                        response.data?.let { NotesState(notes = it, noteOrder = noteOrder) }!!
                }
            }
        }.launchIn(viewModelScope)
    }
}