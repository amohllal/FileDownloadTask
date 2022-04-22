package com.ahmed.data.datasource

import com.ahmed.data.mapper.getFileList
import com.ahmed.data.model.FileResponse
import io.reactivex.Single

interface LocalDataSource {
    fun getLocalFileList() : Single<List<FileResponse>>
}