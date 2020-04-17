package com.example.kotlindemo.restapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.CustomData
import com.example.kotlindemo.R
import com.example.kotlindemo.restapi.models.ListResourceModel
import kotlinx.android.synthetic.main.activity_gallery.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.simpleName as String
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var listResponseAdapter: ListResponseAdapter? = null
    private var apiServices = APIServices.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        toolbar.setNavigationOnClickListener { onBackPressed() }

        recyclerView = findViewById(R.id.recycler_view)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val loginCall = apiServices.getResources(2)
        loginCall.enqueue(object : Callback<ListResourceModel> {
            override fun onFailure(call: Call<ListResourceModel>?, t: Throwable?) {
                CustomData.printToast(this@HomeActivity, t!!.message.toString())
            }

            override fun onResponse(
                call: Call<ListResourceModel>?,
                response: Response<ListResourceModel>?
            ) {
                println("Some: " + response!!.body())
                if (response.isSuccessful) {
                    listResponseAdapter =
                        ListResponseAdapter(this@HomeActivity, response.body()!!.data)
                    recyclerView.adapter = listResponseAdapter
                }
            }
        })

    }
}
