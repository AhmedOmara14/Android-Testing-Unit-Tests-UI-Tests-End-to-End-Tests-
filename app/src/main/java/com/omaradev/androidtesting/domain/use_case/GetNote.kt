package com.omaradev.androidtesting.domain.use_case

import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.repository.Repository
import com.omaradev.androidtesting.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNote(
    private val repository: Repository
) {
    operator fun invoke(
        id: Int
    ): Flow<Resource<Note>> = flow {
        try {
            emit(Resource.Loading<Note>())
            val note = repository.getNoteById(id)
            emit(Resource.Success<Note>(note))
        } catch (e: Exception) {
            emit(Resource.Error<Note>(e.localizedMessage ?: "an Error Occurred"))
        }
    }
}