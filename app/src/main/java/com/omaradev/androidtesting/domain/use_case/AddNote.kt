package com.omaradev.androidtesting.domain.use_case

import com.omaradev.androidtesting.domain.model.InvalidNoteException
import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.repository.Repository

class AddNote(
    private val repository: Repository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if(note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        repository.insertNote(note)
    }
}