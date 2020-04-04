package com.example.kotlindemo.gallery

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {

    private val TAG = GalleryActivity::class.simpleName as String
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var galleryAdapter: GalleryAdapter? = null
    private val galleryModelList: MutableList<GalleryModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        toolbar.setNavigationOnClickListener { onBackPressed() }

        recyclerView = findViewById(R.id.recycler_view)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID
        )

        // exclude media files, they would be here also.
//        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "=="
//                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)

        val cursor: Cursor? = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        )

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {

                val title = cursor.getString(cursor.getColumnIndex(projection[1]))
                val bucketId = cursor.getInt(cursor.getColumnIndex(projection[2]))

                if (title == null)
                    continue

                var idExist = false
                for (galleryModel in galleryModelList) {
                    if (galleryModel.bucketID == bucketId) {
                        galleryModel.imageCount += 1
                        idExist = true;
                        break
                    }
                }

                if (idExist)
                    continue

                galleryModelList.add(
                    GalleryModel(
                        cursor.getString(cursor.getColumnIndex(projection[0])),
                        cursor.getString(cursor.getColumnIndex(projection[1])),
                        cursor.getInt(cursor.getColumnIndex(projection[2]))
                    )
                )
            }

            cursor.close()
        }

//        for (i in 1..6){
//            galleryModelList.add(GalleryModel("Title: $i"))
//        }

        galleryAdapter = GalleryAdapter(this, galleryModelList)
        recyclerView.adapter = galleryAdapter

    }
}
