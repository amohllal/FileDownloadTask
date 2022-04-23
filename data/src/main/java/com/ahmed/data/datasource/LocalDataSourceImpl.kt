package com.ahmed.data.datasource

import com.ahmed.data.mapper.getFileList
import com.ahmed.domain.model.File
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.observable.ObservableCreate
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val downloadFile: DownloadFile) :
    LocalDataSource {
    override fun getLocalFileList() = getFileList()

    override fun downloadingFile(file: File): Observable<File> {
        return Observable.create {
          val listener  = object : DownloadFile.ProgressListener {
                var firstUpdate = true
                override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    if (done) {
                        it.onNext(file)
                        it.onComplete()
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
                        it.onNext(file)

                    }
                }
            }
            downloadFile.progressListener = listener
            downloadFile.invoke(file.url)
        }
    }
}