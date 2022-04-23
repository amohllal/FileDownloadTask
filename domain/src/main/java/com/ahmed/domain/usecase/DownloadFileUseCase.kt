package com.ahmed.domain.usecase

import com.ahmed.domain.model.File
import com.ahmed.domain.repository.FileRepository
import javax.inject.Inject

class DownloadFileUseCase @Inject constructor(private val fileRepository: FileRepository) {
    fun downloadFile(file :File) = fileRepository.downloadingFile(file)
}