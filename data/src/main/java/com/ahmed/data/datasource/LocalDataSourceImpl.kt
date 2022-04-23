package com.ahmed.data.datasource

import android.util.Log
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
            downloadFile.progressListener = object : ProgressListener {
                override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    if (done) {
                        it.onNext(file)
                        it.onComplete()
                    } else {
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

                override fun failure(contentLength: Long) {
                    file.progressLoading = -1
                    it.onNext(file)
                }
            }
            downloadFile.invoke(file)
        }
    }
}