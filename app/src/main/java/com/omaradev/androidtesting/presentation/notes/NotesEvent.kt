package com.omaradev.androidtesting.presentation.notes

import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
