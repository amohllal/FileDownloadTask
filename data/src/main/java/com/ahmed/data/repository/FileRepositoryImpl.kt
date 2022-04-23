package com.ahmed.data.repository

import com.ahmed.data.datasource.LocalDataSource
import com.ahmed.data.datasource.LocalDataSourceImpl
import com.ahmed.data.mapper.mapToDomain
import com.ahmed.domain.model.File
import com.ahmed.domain.repository.FileRepository
import io.reactivex.Single
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource): FileRepository {

    override fun getFilesList(): Single<List<File>> {
        return localDataSource.getLocalFileList().map{
            it.mapToDomain()
        }
    }

    override fun downloadingFile(file: File): Single<File> {
        return localDataSource.downloadingFile(file)
    }

}