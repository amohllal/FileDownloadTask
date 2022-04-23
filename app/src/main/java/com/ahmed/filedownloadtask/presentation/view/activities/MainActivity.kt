package com.ahmed.filedownloadtask.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ahmed.filedownloadtask.R
import com.ahmed.filedownloadtask.presentation.viewmodel.FileViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.ahmed.domain.model.File
import com.ahmed.filedownloadtask.presentation.core.wrapper.DataStatus
import com.ahmed.filedownloadtask.presentation.view.adapter.FileRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val fileViewModel: FileViewModel by viewModels()
    private val fileList: ArrayList<File> = ArrayList()
    private lateinit var fileRecyclerAdapter: FileRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        fileViewModel.getFileList()
        fileViewModel.fileListLiveData.observe(this) {
            when (it?.status) {
                DataStatus.Status.LOADING -> showLoading()
                DataStatus.Status.SUCCESS -> handleSuccessData(it.data)
                DataStatus.Status.ERROR -> handleError()
                else -> {}
            }
        }
    }

    private fun initRecyclerView() {
        fileRecyclerAdapter = FileRecyclerAdapter(fileList, {
            downloadFileWithProgress(it!!)
            Log.d("TAG", "initRecyclerView: ${it.url}")
        }, this)
        file_rv.adapter = fileRecyclerAdapter
    }

    private fun downloadFileWithProgress(file : File){
        fileViewModel.downloadFile(file)
        fileViewModel.downloadingState.observe(this) {
            fileRecyclerAdapter.initProgressLoading(it)
        }

    }

    private fun handleSuccessData(data: List<File>?) {
        hideLoading()
        fileList.addAll(data!!)
        fileRecyclerAdapter.notifyDataSetChanged()
    }

    private fun handleError() {
        hideLoading()
    }


    private fun showLoading() {
        progress_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progress_loading.visibility = View.INVISIBLE
    }
}