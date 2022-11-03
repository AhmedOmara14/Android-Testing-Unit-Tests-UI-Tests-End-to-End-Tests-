package com.omaradev.androidtesting.presentation.notes

import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.util.NoteOrder
import com.omaradev.androidtesting.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
