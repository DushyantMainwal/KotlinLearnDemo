package com.example.kotlindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.example.kotlindemo.gallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainGridAdapter.OnGridItemListener {

    private lateinit var gridView: GridView
    private lateinit var gridAdapter: MainGridAdapter
    private var gridModelList: MutableList<GridModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gridModelList.add(GridModel("Gallery", R.drawable.ic_android_black_24dp))
        for (i in 1..6) {
            gridModelList.add(GridModel("Title $i", R.drawable.ic_android_black_24dp))
        }

        gridView = findViewById(R.id.grid_view)
        gridAdapter = MainGridAdapter(this, R.layout.main_grid_listitem, gridModelList, this)
        gridView.adapter = gridAdapter

//        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id ->
//            Toast.makeText(this@MainActivity, " Clicked Position: " + (position + 1),
//                Toast.LENGTH_SHORT).show()
//        }

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, gridModelList[position].title, Toast.LENGTH_SHORT).show()

        when(position) {
            0 -> startActivity(Intent(this@MainActivity, GalleryActivity::class.java))
        }
    }
}
