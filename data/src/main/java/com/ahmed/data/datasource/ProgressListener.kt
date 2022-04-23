package com.ahmed.data.datasource

interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    fun failure(contentLength: Long)

}