package com.omaradev.androidtesting.domain.repository

import com.omaradev.androidtesting.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getNotes(): List<Note>

    suspend fun getNoteById(id: Int): Note

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}