package com.ahmed.filedownloadtask.presentation.view.adapter

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.domain.model.File
import com.ahmed.filedownloadtask.R
import kotlinx.android.synthetic.main.file_item.view.*

class FileRecyclerAdapter(
    private val fileList: ArrayList<File>,
    private val itemClick: (File?) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FileRecyclerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.file_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FileRecyclerAdapter.ViewHolder, position: Int) {
        val item: File = fileList[position]
        holder.fileName.text = item.name
        holder.fileType.text = item.type
        holder.fileDownload.setOnClickListener {
            itemClick.invoke(item)
            holder.fileProgress.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        if (payloads.firstOrNull() != null) {
            val bundle = payloads.first() as Bundle
            val progress = bundle.getInt("progressLoading")
            if (progress != -1) {
                holder.fileProgress.progress = progress
                holder.downloadPercentage.text = "$progress%"
                if (progress == 100) {
                    handleDownloadedItem(holder)
                }
            } else {
                holder.fileProgress.isIndeterminate = progress == -1
                handleFailedDownload(holder)
            }
        }
    }

    private fun handleFailedDownload(holder: FileRecyclerAdapter.ViewHolder) {
        holder.fileProgress.visibility = View.INVISIBLE
        holder.fileDownload.setTextColor(context.getColor(R.color.purple_700))
        holder.fileDownload.text = "Failed"
        holder.fileDownload.isClickable = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleDownloadedItem(holder: ViewHolder){
        holder.fileProgress.visibility = View.INVISIBLE
        holder.fileDownload.setTextColor(context.getColor(R.color.purple_500))
        holder.fileDownload.text = "Downloaded"
        holder.fileDownload.isClickable = false

    }
    fun initProgressLoading(file: File) {
        val selectedFile = selectFile(file)
        notifyItemChanged(fileList.indexOf(selectedFile), Bundle().apply {
            putInt("progressLoading", file.progressLoading)
        })
    }

    private fun selectFile(file: File) = fileList.find { file.id == it.id }

    override fun getItemCount() = fileList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        var fileName: TextView = view.file_name_tv
        var fileType: TextView = view.file_type_tv
        var fileDownload: TextView = view.download_tv
        var downloadPercentage: TextView = view.percentage_tv
        var fileProgress: ProgressBar = view.download_loading
    }

}