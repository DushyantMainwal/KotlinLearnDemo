package com.example.kotlindemo.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.restapi.models.PantonData

class ListResponseAdapter(private val context: Context, private val pantonDataList: List<PantonData>) :
    RecyclerView.Adapter<ListResponseAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.gallery_item, parent, false)) {
        private var mTitleView: TextView? = null
        private var mDescriptionView: TextView? = null
        private var mLinearLayout: LinearLayout? = null

        init {
            mTitleView = itemView.findViewById(R.id.image_title)
            mDescriptionView = itemView.findViewById(R.id.image_description)
            mLinearLayout = itemView.findViewById(R.id.gallery_ll)
        }

        fun bind(pantonData: PantonData, context: Context) {
            mTitleView?.text = pantonData.name
            mDescriptionView?.text  = pantonData.pantoneValue + " - " + pantonData.color
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GalleryViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = pantonDataList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val pantonData = pantonDataList[position]
        holder.bind(pantonData, context)
    }

}