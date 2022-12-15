package com.thoughtctl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.thoughtctl.model.SearchItem
import com.thoughtctl.R
import com.thoughtctl.util.Utils

import kotlinx.android.synthetic.main.item_pics.view.*


class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.IssuesViewHolder>() {

    inner class IssuesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssuesViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_pics,
            parent,
            false
        )
    )
    override fun getItemCount() =  differ.currentList.size

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        val issueItem = differ.currentList[position]
        holder.itemView.apply {
            if(issueItem.images.isNullOrEmpty())
            {
                ivImage.load(issueItem.link)
                tvImagesCount.text = "Additional Images : 1"
            }
            else
            {
                for(i in 0..issueItem.images.size-1)
                {
                    if(issueItem.images.get(i).type.contains("image"))
                    {
                        ivImage.load(issueItem.images.get(i).link)
                        break
                    }
                }
                tvImagesCount.text = "Additional Images : "+issueItem.images.size
            }
            tvTitle.text = "Title : "+ issueItem.title
            tvDate.text = "Date : "+ Utils.getDateFromTimestamp(issueItem.datetime!!)
        }
    }

}