package com.ahmed.domain.usecase

import com.ahmed.domain.repository.FileRepository
import javax.inject.Inject

class GetFileListUseCase @Inject constructor(private val repo : FileRepository) {
    fun execute() = repo.getFilesList()
}