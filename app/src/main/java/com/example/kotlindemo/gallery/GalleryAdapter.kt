package com.example.kotlindemo.gallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class GalleryAdapter(private val context: Context, private val galleryModelList: List<GalleryModel>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

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

        fun bind(galleryModel: GalleryModel, context: Context) {
            mTitleView?.text = galleryModel.bucketTitle
            val str =  galleryModel.imageCount.toString() + " Images"
            mDescriptionView?.text  = str

            mLinearLayout?.setOnClickListener {
                val  intent = Intent(context, ImagesActivity::class.java)
                intent.putExtra("bucketId", galleryModel.bucketID.toString())
                intent.putExtra("bucketName", galleryModel.bucketTitle)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GalleryViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = galleryModelList.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val galleryModel = galleryModelList[position]
        holder.bind(galleryModel, context)
    }

}