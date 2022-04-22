package com.ahmed.filedownloadtask.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.domain.model.File
import com.ahmed.filedownloadtask.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.file_item.view.*

class FileRecyclerAdapter(
    private val reviewList: ArrayList<File>,
    private val itemClick: (File?) -> Unit,

    private val context: Context
) :
    RecyclerView.Adapter<FileRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FileRecyclerAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.file_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FileRecyclerAdapter.ViewHolder, position: Int) {
        val item: File = reviewList[position]

        holder.fileName.text = item.name
        holder.fileType.text = item.type

        holder.fileDownload.setOnClickListener {
            itemClick.invoke(item)
        }

    }

    override fun getItemCount() = reviewList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        var fileName: TextView = view.file_name_tv
        var fileType: TextView = view.file_type_tv
        var fileDownload: TextView = view.download_tv
        var fileProgress: ProgressBar = view.download_loading
    }

}