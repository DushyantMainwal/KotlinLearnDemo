package com.example.kotlindemo.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.CustomData
import com.example.kotlindemo.R
import java.io.File

class ImagesAdapter(private val context: Context, private val imageModelList: List<ImageModel>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    class ImageViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.images_item, parent, false)) {
        private var mTitleView: TextView? = null
        private var mImageView: ImageView? = null
        private var mLinearLayout: LinearLayout? = null

        init {
            mTitleView = itemView.findViewById(R.id.image_title)
            mImageView = itemView.findViewById(R.id.image_iv)
            mLinearLayout = itemView.findViewById(R.id.image_ll)
        }

        fun bind(imageModel: ImageModel, context: Context) {
            mTitleView?.text = imageModel.imageTitle

            val file = File(imageModel.imagePath);
            if (file.exists()) {
                val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
                mImageView?.setImageBitmap(CustomData.getResizedBitmap(bitmap, 400))
            }

            mLinearLayout?.setOnClickListener {
                val intent = Intent(context, ShowImageActivity::class.java)
                intent.putExtra("imagePath", imageModel.imagePath)
                intent.putExtra("imageName", imageModel.imageTitle)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = imageModelList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val galleryModel = imageModelList[position]
        holder.bind(galleryModel, context)
    }

}