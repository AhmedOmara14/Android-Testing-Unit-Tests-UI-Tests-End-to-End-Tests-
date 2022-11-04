package com.omaradev.androidtesting.domain.use_case

import android.util.Log
import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.repository.Repository
import com.omaradev.androidtesting.util.NoteOrder
import com.omaradev.androidtesting.util.OrderType
import com.omaradev.androidtesting.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: Repository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Resource<List<Note>>> = flow {
        try {
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> repository.getNotes().sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> repository.getNotes().sortedBy { it.timestamp }
                        is NoteOrder.Color -> repository.getNotes().sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> repository.getNotes().sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> repository.getNotes().sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> repository.getNotes().sortedByDescending { it.color }
                    }
                }
            }
            emit(
                Resource.Success<List<Note>>(repository.getNotes())
            )
        } catch (e: Exception) {
            emit(Resource.Error<List<Note>>(e.localizedMessage ?: "an Error Occurred"))
        }
    }


}