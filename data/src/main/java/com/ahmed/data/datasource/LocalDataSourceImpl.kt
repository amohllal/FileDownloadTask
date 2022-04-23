package com.ahmed.data.datasource

import com.ahmed.data.mapper.getFileList
import com.ahmed.domain.model.File
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val downloadFile: DownloadFile) :
    LocalDataSource {
    override fun getLocalFileList() = getFileList()

    override fun downloadingFile(file: File): Single<File> {
        return Single.create { singleEmitter ->
            downloadFile.progressListener = object : DownloadFile.ProgressListener {
                var firstUpdate = true
                override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    if (done) {
                        singleEmitter.onSuccess(file)
                        println("completed")
                    } else {
                        if (firstUpdate) {
                            firstUpdate = false
                            if (contentLength == -1L) {
                                println("content-length: unknown")
                            } else {
                                System.out.format("content-length: %d\n", contentLength)
                            }
                        }
                        if (contentLength == -1L) {
                            file.progressLoading = -1
                        } else {
                            val progress = 100 * bytesRead / contentLength
                            file.progressLoading = progress.toInt()
                        }
                        file.isLoading = done.not()
                        singleEmitter.onSuccess(file)

                    }
                }
            }
            downloadFile.invoke(file.url)
        }
    }
}