package com.example.kotlindemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlindemo.gallery.GalleryActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainGridAdapter.OnGridItemListener {

    private lateinit var gridView: GridView
    private lateinit var gridAdapter: MainGridAdapter
    private var gridModelList: MutableList<GridModel> = mutableListOf()

    private val requiredPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 110
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gridModelList.add(GridModel("Gallery", R.drawable.ic_photo_library_black_24dp))
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

        if (!hasPermissions(this, *requiredPermissions)) {
            ActivityCompat.requestPermissions(
                this,
                requiredPermissions,
                PERMISSIONS_REQUEST_CODE
            )
        }

    }

    private fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    //vararg is spread operator
    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (!hasPermission(context, permission)) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) { // If request is cancelled, the result arrays are empty.
            if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                finish()
            }
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, gridModelList[position].title, Toast.LENGTH_SHORT).show()

        when (position) {
            0 -> startActivity(Intent(this@MainActivity, GalleryActivity::class.java))
        }
    }
}
