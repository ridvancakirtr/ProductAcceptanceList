package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.myapplication.Retrofit.Retrofit
import com.example.myapplication.Model.UserInformation
import com.example.myapplication.Responce.Responce
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error


class LoginActivity : AppCompatActivity(), SaveSharedPreferences {
    lateinit var USER_ID: String
    lateinit var NAME_SURNAME: String
    lateinit var TOKEN: String
    lateinit var username: EditText
    lateinit var password: EditText
    var settingFragment: SettingFragment = SettingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.hide()
        getRetrofitURL()
        settingIcon.setOnClickListener {
            settingFragment.show(supportFragmentManager, null)
        }

        sign_in.setOnClickListener {

            username = findViewById(R.id.username)
            password = findViewById(R.id.password)
            //Toast.makeText(this,username.text.toString()+""+password.text.toString(),Toast.LENGTH_SHORT).show()
            try {
                Retrofit().getClient(getRetrofitURL()).create(Responce::class.java)
                    .getUserInformation(username.text.toString(), password.text.toString())
                    .enqueue(object :
                        Callback<UserInformation> {
                        override fun onFailure(call: Call<UserInformation>, t: Throwable) {
                            Log.d("TAG FAIL", t.message + " > " + call.request())

                            val contextView = findViewById<View>(android.R.id.content)
                            val snackbar =
                                Snackbar.make(contextView, "Sunucu Bağlantı Hatası", Snackbar.LENGTH_SHORT)
                            snackbar.show()

                            //Toast.makeText(this@LoginActivity,"Username or Password NotCorrect", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<UserInformation>,
                            response: Response<UserInformation>
                        ) {
                            Log.d(
                                "TAG OK",
                                response.body()!!.Successful.toString() + " > " + call.request()
                            )
                            if (response.body()!!.Successful.toString().toBoolean()) {

                                USER_ID = response.body()!!.Kullanici_id.toString()
                                NAME_SURNAME = response.body()!!.AdSoyad.toString()
                                TOKEN = response.body()!!.Token.toString()
                                StartActivity()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Username or Password NotCorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    })
            } catch (e: Error) {
                val contextView = findViewById<View>(android.R.id.content)
                val snackbar =
                    Snackbar.make(contextView, "Sunucu Bağlantı Hatası", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }

        }


    }

    fun StartActivity() {
        val homeIntent = Intent(this@LoginActivity, MainActivity::class.java)
        saveUserData(USER_ID, NAME_SURNAME, TOKEN)
        startActivity(homeIntent)
    }

    fun saveUserData(USER_ID: String, NAME_SURNAME: String, TOKEN: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("USER_ID", USER_ID)
        editor.putString("NAME_SURNAME", NAME_SURNAME)
        editor.putString("TOKEN", TOKEN)
        editor.apply() //Kayıt.

    }

    override fun submittedInformation(ipaddress: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("IP_ADDRESS", ipaddress)
        editor.apply() //Kayıt.
        Toast.makeText(this, "Saved : $ipaddress", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofitURL(): String {
        val sharedPref = this.getSharedPreferences("LoginActivity", Context.MODE_PRIVATE)
        val ipaddress = sharedPref.getString("IP_ADDRESS", "kayityok")
        return ipaddress!!
    }
}
