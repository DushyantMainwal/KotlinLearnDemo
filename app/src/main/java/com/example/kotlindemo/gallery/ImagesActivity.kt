package com.example.kotlindemo.gallery

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.activity_gallery.*

class ImagesActivity : AppCompatActivity() {

    private val TAG = ImagesActivity::class.simpleName as String
    private lateinit var recyclerView: RecyclerView
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private val imageModelList: MutableList<ImageModel> = mutableListOf()

    private var imagesAdapter: ImagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        recyclerView = findViewById(R.id.recycler_view)
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager

        val bucketId = intent.getStringExtra("bucketId") ?: return
        supportActionBar?.title = intent.getStringExtra("bucketName")

        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.SIZE
        )

        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            MediaStore.Images.Media.BUCKET_ID + " = ?", arrayOf<String>(bucketId),
            MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        )

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                val title = cursor.getString(cursor.getColumnIndex(projection[1]))
                val path = cursor.getString(cursor.getColumnIndex(projection[0]))

                if (title == null || path == null)
                    continue
                imageModelList.add(ImageModel(title, path))
            }
            cursor.close()
        }

        imagesAdapter = ImagesAdapter(this, imageModelList)
        recyclerView.adapter = imagesAdapter
    }
}
