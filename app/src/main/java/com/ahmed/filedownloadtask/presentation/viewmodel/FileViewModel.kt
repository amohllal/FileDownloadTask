package com.ahmed.filedownloadtask.presentation.viewmodel

import android.util.AndroidException
import androidx.lifecycle.MutableLiveData
import com.ahmed.domain.model.File
import com.ahmed.domain.usecase.DownloadFileUseCase
import com.ahmed.domain.usecase.GetFileListUseCase
import com.ahmed.filedownloadtask.presentation.core.BaseViewModel
import com.ahmed.filedownloadtask.presentation.core.wrapper.StateLiveData
import com.ahmed.filedownloadtask.presentation.di.RxModule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

@HiltViewModel
class FileViewModel @Inject constructor(
    private val getFileListUseCase: GetFileListUseCase,
    private val downloadFileUseCase: DownloadFileUseCase,
    @Named("IO") private val ioScheduler: Scheduler,
    @Named("Main") private val mainScheduler : Scheduler)
    : BaseViewModel() {

    val fileListLiveData by lazy { StateLiveData<List<File>?>() }
    val downloadingState by lazy { MutableLiveData<File>() }

    fun getFileList() {
        compositeDisposable.add(
            getFileListUseCase.getFileList()
                .subscribeOn(ioScheduler)
                .doOnSubscribe {
                    fileListLiveData.postLoading()
                }
                .observeOn(mainScheduler)
                .subscribe({ response ->
                    fileListLiveData.postSuccess(response)
                }, { error ->
                    fileListLiveData.postError(error)
                })
        )
    }

    fun downloadFile(request : File){
        compositeDisposable.add(
            downloadFileUseCase.downloadFile(request)
                .subscribeOn(ioScheduler)
                .doOnSubscribe { downloadingState.postValue(request) }
                .observeOn(mainScheduler)
                .subscribe { response ->
                    downloadingState.postValue(response)
                }
        )
    }


}