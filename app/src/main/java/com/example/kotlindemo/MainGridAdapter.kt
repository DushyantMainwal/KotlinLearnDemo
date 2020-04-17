package com.example.kotlindemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainGridAdapter(
    context: Context, private val resourceLayout: Int,
    private val gridModelList: MutableList<GridModel>, gridItemListener: OnGridItemListener
) :
    ArrayAdapter<MainGridAdapter.ItemViewHolder>(context, resourceLayout) {

    var itemListener: OnGridItemListener = gridItemListener

    override fun getCount(): Int {
        return gridModelList.size
    }

    override fun getView(position: Int, convertViewOrg: View?, parent: ViewGroup): View {
        var convertView = convertViewOrg
        val itemHolder: ItemViewHolder

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resourceLayout, null)
            itemHolder = ItemViewHolder()
            itemHolder.iconIV = convertView!!.findViewById(R.id.icon_iv)
            itemHolder.titleTV = convertView.findViewById(R.id.title_tv)
            itemHolder.mainLL = convertView.findViewById(R.id.main_ll)
            convertView.tag = itemHolder
        } else
            itemHolder = convertView.tag as ItemViewHolder

        itemHolder.titleTV!!.text = gridModelList[position].title
        itemHolder.iconIV!!.setImageResource(gridModelList[position].imageId)

        itemHolder.mainLL!!.setOnClickListener {
            itemListener.onItemClick(position)
        }

        return convertView
    }

    class ItemViewHolder {
        var iconIV: ImageView? = null
        var titleTV: TextView? = null
        var mainLL: LinearLayout? = null
    }

    interface OnGridItemListener {
        fun onItemClick(position: Int)
    }
}