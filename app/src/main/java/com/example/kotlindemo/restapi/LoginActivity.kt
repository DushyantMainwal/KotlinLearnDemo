package com.example.kotlindemo.restapi

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.CustomData
import com.example.kotlindemo.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameET: EditText
    private lateinit var passwordET: EditText
    private var apiServices = APIServices.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Login"

        toolbar.setNavigationOnClickListener { onBackPressed() }

        usernameET = findViewById(R.id.username_et)
        passwordET = findViewById(R.id.password_et)
        usernameET.setText("eve.holt@reqres.in")
        passwordET.setText("cityslicka")

        login_btn.setOnClickListener {
            if (isEmptyFields()) {
                CustomData.printToast(this@LoginActivity, "Empty Fields")
                return@setOnClickListener
            }

            val loginCall =
                apiServices.loginUser(usernameET.text.toString(), passwordET.text.toString())
            loginCall.enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    CustomData.printToast(this@LoginActivity, t!!.message.toString())
                }

                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    println("Some: " + response!!.body())

                    if (response.isSuccessful) {
                        try {
                            val jsonObject = JSONObject(response.body().toString())
                            CustomData.printToast(
                                this@LoginActivity, "Login Success: "
                                        + jsonObject.optString("token")
                            )
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } else {
                        CustomData.printToast(this@LoginActivity, "Data Not Correct")
                    }
                }
            })
        }

    }

    private fun isEmptyFields(): Boolean {
        val usernameLoginText: String = usernameET.getText().toString()
        val passwordLoginText: String = passwordET.getText().toString()
        return usernameLoginText.isEmpty() || passwordLoginText.isEmpty()
    }

}
