package com.ahmed.domain.repository

import com.ahmed.domain.model.File
import io.reactivex.Observable
import io.reactivex.Single

interface FileRepository {
    fun getFilesList() : Single<List<File>>

    fun downloadingFile(file: File)  : Observable<File>
}