package com.omaradev.androidtesting.domain.use_case

import com.omaradev.androidtesting.domain.model.Note
import com.omaradev.androidtesting.domain.repository.Repository
import com.omaradev.androidtesting.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNote(
    private val repository: Repository
) {
    operator fun invoke(
        note: Note
    ): Flow<Resource<*>> = flow {
        try {
            emit(Resource.Loading<Any>())
            repository.deleteNote(note)
            emit(Resource.Success<Any>("Success"))
        } catch (e:Exception) {
            emit(Resource.Error<Any>(e.localizedMessage ?: "an Error Occurred"))
        }
    }
}