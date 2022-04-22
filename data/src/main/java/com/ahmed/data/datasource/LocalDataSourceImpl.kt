package com.ahmed.data.datasource

import com.ahmed.data.mapper.getFileList
import com.ahmed.data.model.FileResponse
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl : LocalDataSource {
    override fun getLocalFileList() = getFileList()
}