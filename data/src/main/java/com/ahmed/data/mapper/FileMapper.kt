package com.ahmed.data.mapper

import com.ahmed.data.model.FileResponse
import com.ahmed.data.model.RESPONSE
import com.ahmed.domain.model.File
import com.google.gson.GsonBuilder
import io.reactivex.Single

fun getFileList(): Single<List<FileResponse>> {
    val gson = GsonBuilder().create()
    return Single.just(gson.fromJson(RESPONSE, Array<FileResponse>::class.java).toList())
}

fun List<FileResponse>.mapToDomain() = this.map {
    File(id = it.id, type = it.type, url = it.url, name = it.name)
}
