package com.omaradev.androidtesting.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.omaradev.androidtesting.data.FakeRepository
import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.util.NoteOrder
import com.omaradev.androidtesting.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetNotesTest {
    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeRepository = FakeRepository()
        getNotes = GetNotes(fakeRepository)
        val notes = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notes.add(
                Note(
                    c.toString(),
                    c.toString(),
                    index.toLong(),
                    index
                )
            )
        }

        notes.shuffle()
        runBlocking {
            notes.forEach { note ->
                fakeRepository.insertNote(note = note)
            }
        }
    }

    @Test
    fun `notes by title ascending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isLessThan(notes[i+1].title)
        }
    }

    @Test
    fun `notes by title descending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Title(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].title).isGreaterThan(notes[i+1].title)
        }
    }

    @Test
    fun `notes by Date ascending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].timestamp).isLessThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `notes by Date descending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Date(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].timestamp).isGreaterThan(notes[i+1].timestamp)
        }
    }

    @Test
    fun `notes by Color ascending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Ascending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].color).isLessThan(notes[i+1].color)
        }
    }

    @Test
    fun `notes by Color descending , return true`() = runBlocking {
        val notes = getNotes(NoteOrder.Color(OrderType.Descending)).first()

        for (i in 0..notes.size-2){
            assertThat(notes[i].color).isGreaterThan(notes[i+1].color)
        }
    }
}