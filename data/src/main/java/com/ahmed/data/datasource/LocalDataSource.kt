package com.ahmed.data.datasource

import com.ahmed.data.mapper.getFileList
import com.ahmed.data.model.FileResponse
import com.ahmed.domain.model.File
import io.reactivex.Observable
import io.reactivex.Single

interface LocalDataSource {
    fun getLocalFileList() : Single<List<FileResponse>>

    fun downloadingFile(file: File)  : Observable<File>

}