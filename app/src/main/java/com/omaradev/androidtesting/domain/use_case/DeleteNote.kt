package com.omaradev.androidtesting.domain.use_case

import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.repository.Repository

class DeleteNote(
    private val repository: Repository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}