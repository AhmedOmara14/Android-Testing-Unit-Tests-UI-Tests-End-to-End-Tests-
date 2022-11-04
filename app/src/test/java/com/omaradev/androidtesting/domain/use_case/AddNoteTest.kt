package com.omaradev.androidtesting.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.omaradev.androidtesting.data.FakeRepository
import com.omaradev.androidtesting.domain.model.InvalidNoteException
import com.omaradev.androidtesting.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class AddNoteTest {
    private lateinit var addNote: AddNote
    private lateinit var fakeRepository: FakeRepository
    private lateinit var getNotes: GetNotes

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        addNote = AddNote(fakeRepository)
        getNotes= GetNotes(fakeRepository)
    }
    @Test
    fun `insert data with empty title , return error`() = runBlocking {
        val note = Note(
            title = "",
            content = "testBody",
            timestamp = 1,
            color = 1
        )
        fakeRepository.insertNote(note = note)
        val notes = getNotes().first()
        assertFalse(notes.contains(note))
    }


}